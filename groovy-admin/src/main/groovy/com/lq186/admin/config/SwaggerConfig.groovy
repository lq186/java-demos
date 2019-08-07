package com.lq186.admin.config

import com.fasterxml.classmate.ResolvedType
import com.fasterxml.classmate.TypeResolver
import com.fasterxml.classmate.members.ResolvedField
import com.fasterxml.classmate.members.ResolvedMethod
import com.fasterxml.classmate.members.ResolvedParameterizedMember
import com.fasterxml.jackson.databind.BeanDescription
import com.fasterxml.jackson.databind.DeserializationConfig
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationConfig
import com.fasterxml.jackson.databind.introspect.AnnotatedField
import com.fasterxml.jackson.databind.introspect.AnnotatedMember
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod
import com.fasterxml.jackson.databind.introspect.AnnotatedParameter
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
import com.google.common.annotations.VisibleForTesting
import com.google.common.base.Equivalence
import com.google.common.base.Function
import com.google.common.base.Optional
import com.google.common.base.Predicate
import com.google.common.cache.CacheBuilder
import com.google.common.cache.CacheLoader
import com.google.common.cache.LoadingCache
import com.google.common.collect.FluentIterable
import com.google.common.collect.Sets
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.util.ClassUtils
import org.springframework.web.bind.annotation.RestController
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.ModelPropertyBuilder
import springfox.documentation.builders.ParameterBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.schema.Maps
import springfox.documentation.schema.ModelProperty
import springfox.documentation.schema.TypeNameExtractor
import springfox.documentation.schema.Types
import springfox.documentation.schema.configuration.ObjectMapperConfigured
import springfox.documentation.schema.plugins.SchemaPluginsManager
import springfox.documentation.schema.property.BeanPropertyDefinitions
import springfox.documentation.schema.property.BeanPropertyNamingStrategy
import springfox.documentation.schema.property.FactoryMethodProvider
import springfox.documentation.schema.property.ModelPropertiesProvider
import springfox.documentation.schema.property.bean.AccessorsProvider
import springfox.documentation.schema.property.bean.BeanModelProperty
import springfox.documentation.schema.property.bean.ParameterModelProperty
import springfox.documentation.schema.property.field.FieldModelProperty
import springfox.documentation.schema.property.field.FieldProvider
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Parameter
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.schema.AlternateTypeProvider
import springfox.documentation.spi.schema.EnumTypeDeterminer
import springfox.documentation.spi.schema.contexts.ModelContext
import springfox.documentation.spi.schema.contexts.ModelPropertyContext
import springfox.documentation.spi.service.contexts.ParameterExpansionContext
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.spring.web.plugins.DocumentationPluginsManager
import springfox.documentation.spring.web.readers.parameter.ExpansionContext
import springfox.documentation.spring.web.readers.parameter.ModelAttributeField
import springfox.documentation.spring.web.readers.parameter.ModelAttributeParameterExpander
import springfox.documentation.spring.web.readers.parameter.ModelAttributeParameterMetadataAccessor
import springfox.documentation.swagger2.annotations.EnableSwagger2

import java.beans.BeanInfo
import java.beans.IntrospectionException
import java.beans.Introspector
import java.beans.PropertyDescriptor
import java.lang.reflect.Method
import java.util.concurrent.TimeUnit

import static com.google.common.base.Objects.equal
import static com.google.common.base.Predicates.and
import static com.google.common.base.Predicates.not
import static com.google.common.base.Predicates.or
import static com.google.common.base.Strings.isNullOrEmpty
import static com.google.common.collect.FluentIterable.from
import static com.google.common.collect.Iterables.tryFind
import static com.google.common.collect.Lists.newArrayList
import static com.google.common.collect.Maps.uniqueIndex
import static com.google.common.collect.Sets.newHashSet
import static springfox.documentation.schema.Annotations.memberIsUnwrapped
import static springfox.documentation.schema.Annotations.unwrappedPrefix
import static springfox.documentation.schema.Collections.collectionElementType
import static springfox.documentation.schema.Collections.isContainerType
import static springfox.documentation.schema.ResolvedTypes.modelRefFactory
import static springfox.documentation.schema.Types.isVoid
import static springfox.documentation.schema.Types.typeNameFor
import static springfox.documentation.schema.property.BeanPropertyDefinitions.name
import static springfox.documentation.schema.property.bean.BeanModelProperty.paramOrReturnType
import static springfox.documentation.spi.schema.contexts.ModelContext.fromParent

/*    
    Copyright ©2019 lq186.com 
 
    Licensed under the Apache License, Version 2.0 (the "License")
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
 
        http://www.apache.org/licenses/LICENSE-2.0
 
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/
/*
    FileName: SConfig.java
    Date: 2019/8/5
    Author: lq
*/

@EnableSwagger2
@Configuration
class SwaggerConfig {

    @Bean
    Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build()
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Groovy Admin Documents")
                .description("This is document description. Base url: http://localhost:8080/")
                .version("1.0")
                .build()
    }

    @Component
    @Primary
    static class CustomizeModelAttributeParameterExpander extends ModelAttributeParameterExpander {

        private static final Logger LOG = LoggerFactory.getLogger(ModelAttributeParameterExpander.class)

        private final FieldProvider fields

        private final AccessorsProvider accessors

        private final EnumTypeDeterminer enumTypeDeterminer

        @Autowired
        protected DocumentationPluginsManager pluginsManager

        @Autowired
        CustomizeModelAttributeParameterExpander(FieldProvider fields, AccessorsProvider accessors, EnumTypeDeterminer enumTypeDeterminer) {
            super(fields, accessors, enumTypeDeterminer)
            this.fields = fields
            this.accessors = accessors
            this.enumTypeDeterminer = enumTypeDeterminer
        }

        List<Parameter> expand(ExpansionContext context) {
            List<Parameter> parameters = newArrayList()
            Set<PropertyDescriptor> propertyDescriptors = propertyDescriptors(context.getParamType().getErasedType())
            Map<Method, PropertyDescriptor> propertyLookupByGetter = propertyDescriptorsByMethod(context.getParamType().getErasedType(), propertyDescriptors)
            Iterable<ResolvedMethod> getters = FluentIterable.from(accessors.in(context.getParamType()))
                    .filter(onlyValidGetters(propertyLookupByGetter.keySet()))

            Map<String, ResolvedField> fieldsByName = FluentIterable.from(this.fields.in(context.getParamType()))
                    .uniqueIndex(new Function<ResolvedField, String>() {
                @Override
                String apply(ResolvedField input) {
                    return input.getName()
                }
            })


            LOG.debug("Expanding parameter type: {}", context.getParamType())
            final AlternateTypeProvider alternateTypeProvider = context.getDocumentationContext().getAlternateTypeProvider()

            FluentIterable<ModelAttributeField> attributes =
                    allModelAttributes(
                            propertyLookupByGetter,
                            getters,
                            fieldsByName,
                            alternateTypeProvider)

            FluentIterable<ModelAttributeField> expendables = attributes
                    .filter(not(metaClass()))
                    .filter(not(simpleType()))
                    .filter(not(recursiveType(context)))
            for (ModelAttributeField each : expendables) {
                LOG.debug("Attempting to expand expandable property: {}", each.getName())
                parameters.addAll(
                        expand(
                                context.childContext(
                                        nestedParentName(context.getParentName(), each),
                                        each.getFieldType(),
                                        context.getOperationContext())))
            }

            FluentIterable<ModelAttributeField> collectionTypes = attributes
                    .filter(and(isCollection(), not(recursiveCollectionItemType(context.getParamType()))))
            for (ModelAttributeField each : collectionTypes) {
                LOG.debug("Attempting to expand collection/array field: {}", each.getName())

                ResolvedType itemType = collectionElementType(each.getFieldType())
                if (Types.isBaseType(itemType) || enumTypeDeterminer.isEnum(itemType.getErasedType())) {
                    parameters.add(simpleFields(context.getParentName(), context, each))
                } else {
                    ExpansionContext childContext = context.childContext(
                            nestedParentName(context.getParentName(), each),
                            itemType,
                            context.getOperationContext())
                    if (!context.hasSeenType(itemType)) {
                        parameters.addAll(expand(childContext))
                    }
                }
            }

            FluentIterable<ModelAttributeField> fields = attributes.filter(simpleType())
            for (ModelAttributeField each : fields) {
                parameters.add(simpleFields(context.getParentName(), context, each))
            }
            return FluentIterable.from(parameters)
                    .filter(not(hiddenParameters()))
                    .filter(not(voidParameters()))
                    .toList()
        }

        private FluentIterable<ModelAttributeField> allModelAttributes(
                Map<Method, PropertyDescriptor> propertyLookupByGetter,
                Iterable<ResolvedMethod> getters,
                Map<String, ResolvedField> fieldsByName,
                AlternateTypeProvider alternateTypeProvider) {

            FluentIterable<ModelAttributeField> modelAttributesFromGetters = from(getters)
                    .transform(toModelAttributeField(fieldsByName, propertyLookupByGetter, alternateTypeProvider))

            FluentIterable<ModelAttributeField> modelAttributesFromFields = from(fieldsByName.values())
                    .filter(publicFields())
                    .transform(toModelAttributeField(alternateTypeProvider))

            return FluentIterable.from(Sets.union(
                    modelAttributesFromFields.toSet(),
                    modelAttributesFromGetters.toSet()))
        }

        private Function<ResolvedField, ModelAttributeField> toModelAttributeField(
                final AlternateTypeProvider alternateTypeProvider) {

            return new Function<ResolvedField, ModelAttributeField>() {
                @Override
                ModelAttributeField apply(ResolvedField input) {
                    return new ModelAttributeField(
                            alternateTypeProvider.alternateFor(input.getType()),
                            input.getName(),
                            input,
                            input)
                }
            }
        }

        private Predicate<ResolvedField> publicFields() {
            return new Predicate<ResolvedField>() {
                @Override
                boolean apply(ResolvedField input) {
                    return input.isPublic()
                }
            }
        }

        private Predicate<Parameter> voidParameters() {
            return new Predicate<Parameter>() {
                @Override
                boolean apply(Parameter input) {
                    return isVoid(input.getType().orNull())
                }
            }
        }

        private Predicate<ModelAttributeField> recursiveCollectionItemType(final ResolvedType paramType) {
            return new Predicate<ModelAttributeField>() {
                @Override
                boolean apply(ModelAttributeField input) {
                    return equal(collectionElementType(input.getFieldType()), paramType)
                }
            }
        }

        private Predicate<Parameter> hiddenParameters() {
            return new Predicate<Parameter>() {
                @Override
                boolean apply(Parameter input) {
                    return input.isHidden()
                }
            }
        }

        private Parameter simpleFields(
                String parentName,
                ExpansionContext context,
                ModelAttributeField each) {
            LOG.debug("Attempting to expand field: {}", each)
            String dataTypeName = Optional.fromNullable(typeNameFor(each.getFieldType().getErasedType()))
                    .or(each.getFieldType().getErasedType().getSimpleName())
            LOG.debug("Building parameter for field: {}, with type: ", each, each.getFieldType())
            ParameterExpansionContext parameterExpansionContext = new ParameterExpansionContext(
                    dataTypeName,
                    parentName,
                    determineScalarParameterType(
                            context.getOperationContext().consumes(),
                            context.getOperationContext().httpMethod()),
                    new ModelAttributeParameterMetadataAccessor(
                            each.annotatedElements(),
                            each.getFieldType(),
                            each.getName()),
                    context.getDocumentationContext().getDocumentationType(),
                    new ParameterBuilder())
            return pluginsManager.expandParameter(parameterExpansionContext)
        }

        private Predicate<ModelAttributeField> recursiveType(final ExpansionContext context) {
            return new Predicate<ModelAttributeField>() {
                @Override
                boolean apply(ModelAttributeField input) {
                    return context.hasSeenType(input.getFieldType())
                }
            }
        }

        private Predicate<ModelAttributeField> metaClass() {
            return new Predicate<ModelAttributeField>() {
                @Override
                boolean apply(ModelAttributeField input) {
                    return MetaClass.class.isAssignableFrom(input.getFieldType().getErasedType())
                }
            }
        }

        private Predicate<ModelAttributeField> simpleType() {
            return and(not(isCollection()), not(isMap()),
                    or(
                            belongsToJavaPackage(),
                            isBaseType(),
                            isEnum()))
        }

        private Predicate<ModelAttributeField> isCollection() {
            return new Predicate<ModelAttributeField>() {
                @Override
                boolean apply(ModelAttributeField input) {
                    return isContainerType(input.getFieldType())
                }
            }
        }

        private Predicate<ModelAttributeField> isMap() {
            return new Predicate<ModelAttributeField>() {
                @Override
                boolean apply(ModelAttributeField input) {
                    return Maps.isMapType(input.getFieldType())
                }
            }
        }

        private Predicate<ModelAttributeField> isEnum() {
            return new Predicate<ModelAttributeField>() {
                @Override
                boolean apply(ModelAttributeField input) {
                    return enumTypeDeterminer.isEnum(input.getFieldType().getErasedType())
                }
            }
        }

        private Predicate<ModelAttributeField> belongsToJavaPackage() {
            return new Predicate<ModelAttributeField>() {
                @Override
                boolean apply(ModelAttributeField input) {
                    return ClassUtils.getPackageName(input.getFieldType().getErasedType()).startsWith("java.lang")
                }
            }
        }

        private Predicate<ModelAttributeField> isBaseType() {
            return new Predicate<ModelAttributeField>() {
                @Override
                boolean apply(ModelAttributeField input) {
                    return Types.isBaseType(input.getFieldType()) || input.getFieldType().isPrimitive()
                }
            }
        }

        private Function<ResolvedMethod, ModelAttributeField> toModelAttributeField(
                final Map<String, ResolvedField> fieldsByName,
                final Map<Method, PropertyDescriptor> propertyLookupByGetter,
                final AlternateTypeProvider alternateTypeProvider) {
            return new Function<ResolvedMethod, ModelAttributeField>() {
                @Override
                ModelAttributeField apply(ResolvedMethod input) {
                    String name = propertyLookupByGetter.get(input.getRawMember()).getName()
                    return new ModelAttributeField(
                            fieldType(alternateTypeProvider, input),
                            name,
                            input,
                            fieldsByName.get(name))
                }
            }
        }

        private Predicate<ResolvedMethod> onlyValidGetters(final Set<Method> methods) {
            return new Predicate<ResolvedMethod>() {
                @Override
                boolean apply(ResolvedMethod input) {
                    return methods.contains(input.getRawMember())
                }
            }
        }

        private String nestedParentName(String parentName, ModelAttributeField attribute) {
            String name = attribute.getName()
            ResolvedType fieldType = attribute.getFieldType()
            if (isContainerType(fieldType) && !Types.isBaseType(collectionElementType(fieldType))) {
                name += "[0]"
            }

            if (isNullOrEmpty(parentName)) {
                return name
            }
            return String.format("%s.%s", parentName, name)
        }

        private ResolvedType fieldType(AlternateTypeProvider alternateTypeProvider, ResolvedMethod method) {
            return alternateTypeProvider.alternateFor(method.getType())
        }

        private Set<PropertyDescriptor> propertyDescriptors(final Class<?> clazz) {
            try {
                return FluentIterable.from(getBeanInfo(clazz).getPropertyDescriptors())
                        .toSet()
            } catch (IntrospectionException e) {
                LOG.warn(String.format("Failed to get bean properties on (%s)", clazz), e)
            }
            return newHashSet()
        }

        private Map<Method, PropertyDescriptor> propertyDescriptorsByMethod(
                final Class<?> clazz,
                Set<PropertyDescriptor> propertyDescriptors) {
            return FluentIterable.from(propertyDescriptors)
                    .filter(new Predicate<PropertyDescriptor>() {
                @Override
                boolean apply(PropertyDescriptor input) {
                    return input.getReadMethod() != null && !clazz.isAssignableFrom(Collection.class) && "isEmpty" != input.getReadMethod().getName()
                }
            }).uniqueIndex(new Function<PropertyDescriptor, Method>() {
                @Override
                Method apply(PropertyDescriptor input) {
                    return input.getReadMethod()
                }
            })

        }

        @VisibleForTesting
        BeanInfo getBeanInfo(Class<?> clazz) throws IntrospectionException {
            return Introspector.getBeanInfo(clazz)
        }

        private String determineScalarParameterType(Set<? extends MediaType> consumes, HttpMethod method) {
            String parameterType = "query"

            if (consumes.contains(MediaType.APPLICATION_FORM_URLENCODED)
                    && method == HttpMethod.POST) {
                parameterType = "form"
            } else if (consumes.contains(MediaType.MULTIPART_FORM_DATA)
                    && method == HttpMethod.POST) {
                parameterType = "formData"
            }

            return parameterType
        }

    }

    @Primary
    @Component
    @Qualifier("cachedModelProperties")
    static class CustomizeCachingModelPropertiesProvider implements ModelPropertiesProvider {
        private final Logger LOGGER = LoggerFactory.getLogger(springfox.documentation.schema.property.CachingModelPropertiesProvider.class)
        private final LoadingCache<ModelContext, List<ModelProperty>> cache

        @Autowired
        CustomizeCachingModelPropertiesProvider(
                final TypeResolver resolver,
                @Qualifier("customizeOptimized") final ModelPropertiesProvider delegate) {
            cache = CacheBuilder.newBuilder()
                    .maximumSize(1000)
                    .expireAfterWrite(24, TimeUnit.HOURS)
                    .build(
                    new CacheLoader<ModelContext, List<ModelProperty>>() {
                        List<ModelProperty> load(ModelContext key) {
                            return delegate.propertiesFor(key.resolvedType(resolver), key)
                        }
                    })
        }

        @Override
        List<ModelProperty> propertiesFor(ResolvedType type, ModelContext givenContext) {
            try {
                return cache.get(givenContext)
            } catch (Exception e) {
                LOGGER.warn("Exception calculating properties for model({}) -> {}. {}",
                        type, givenContext.description(), e.getMessage())
                return newArrayList()
            }
        }

        @Override
        void onApplicationEvent(ObjectMapperConfigured event) {
            //No-op
        }
    }

    @Primary
    @Component("customizeOptimized")
    static class CustomizeOptimizedModelPropertiesProvider implements ModelPropertiesProvider {
        private final Logger LOG = LoggerFactory.getLogger(springfox.documentation.schema.property.OptimizedModelPropertiesProvider.class)
        private final AccessorsProvider accessors
        private final FieldProvider fields
        private final FactoryMethodProvider factoryMethods
        private final TypeResolver typeResolver
        private final BeanPropertyNamingStrategy namingStrategy
        private final SchemaPluginsManager schemaPluginsManager
        private final TypeNameExtractor typeNameExtractor
        private ObjectMapper objectMapper

        @Autowired
        CustomizeOptimizedModelPropertiesProvider(
                AccessorsProvider accessors,
                FieldProvider fields,
                FactoryMethodProvider factoryMethods,
                TypeResolver typeResolver,
                BeanPropertyNamingStrategy namingStrategy,
                SchemaPluginsManager schemaPluginsManager,
                TypeNameExtractor typeNameExtractor) {

            this.accessors = accessors
            this.fields = fields
            this.factoryMethods = factoryMethods
            this.typeResolver = typeResolver
            this.namingStrategy = namingStrategy
            this.schemaPluginsManager = schemaPluginsManager
            this.typeNameExtractor = typeNameExtractor
        }

        @Override
        void onApplicationEvent(ObjectMapperConfigured event) {
            objectMapper = event.getObjectMapper()
        }


        @Override
        List<ModelProperty> propertiesFor(ResolvedType type, ModelContext givenContext) {
            List<ModelProperty> syntheticProperties = schemaPluginsManager.syntheticProperties(givenContext)
            if (!syntheticProperties.isEmpty()) {
                return syntheticProperties
            }
            return propertiesFor2(type, givenContext, "")
        }

        private List<ModelProperty> propertiesFor2(ResolvedType type, ModelContext givenContext, String namePrefix) {
            List<ModelProperty> properties = newArrayList()
            BeanDescription beanDescription = beanDescription(type, givenContext)
            List<BeanPropertyDefinition> propertyDefinitions = beanDescription.findProperties()
            List<BeanPropertyDefinition> propertyDefinitionList = new ArrayList<>()
            for (BeanPropertyDefinition definition : propertyDefinitions) {
                if (definition.getName() != "metaClass") {
                    propertyDefinitionList.add(definition)
                }
            }
            Map<String, BeanPropertyDefinition> propertyLookup = uniqueIndex(propertyDefinitionList,
                    BeanPropertyDefinitions.beanPropertyByInternalName())
            for (Map.Entry<String, BeanPropertyDefinition> each : propertyLookup.entrySet()) {
                LOG.debug("Reading property {}", each.getKey())
                BeanPropertyDefinition jacksonProperty = each.getValue()
                Optional<AnnotatedMember> annotatedMember = Optional.fromNullable(safeGetPrimaryMember(jacksonProperty))
                if (annotatedMember.isPresent()) {
                    properties.addAll(candidateProperties(type, annotatedMember.get(), jacksonProperty, givenContext, namePrefix))
                }
            }
            return FluentIterable.from(properties).toSortedSet(byPropertyName()).asList()
        }

        private Comparator<ModelProperty> byPropertyName() {
            return new Comparator<ModelProperty>() {
                @Override
                int compare(ModelProperty first, ModelProperty second) {
                    return first.getName().compareTo(second.getName())
                }
            }
        }

        private AnnotatedMember safeGetPrimaryMember(BeanPropertyDefinition jacksonProperty) {
            try {
                return jacksonProperty.getPrimaryMember()
            } catch (IllegalArgumentException e) {
                LOG.warn(String.format("Unable to get unique property. %s", e.getMessage()))
                return null
            }
        }

        private Function<ResolvedMethod, List<ModelProperty>> propertyFromBean(
                final ModelContext givenContext,
                final BeanPropertyDefinition jacksonProperty,
                final String namePrefix) {

            return new Function<ResolvedMethod, List<ModelProperty>>() {
                @Override
                List<ModelProperty> apply(ResolvedMethod input) {
                    ResolvedType type = paramOrReturnType(typeResolver, input)
                    if (!givenContext.canIgnore(type)) {
                        if (memberIsUnwrapped(jacksonProperty.getPrimaryMember())) {
                            return propertiesFor(
                                    type,
                                    fromParent(givenContext, type),
                                    String.format(
                                            "%s%s",
                                            namePrefix,
                                            unwrappedPrefix(jacksonProperty.getPrimaryMember())))
                        }
                        return newArrayList(beanModelProperty(input, jacksonProperty, givenContext, namePrefix))
                    }
                    return newArrayList()
                }
            }
        }


        private Function<ResolvedField, List<ModelProperty>> propertyFromField(
                final ModelContext givenContext,
                final BeanPropertyDefinition jacksonProperty,
                final String namePrefix) {

            return new Function<ResolvedField, List<ModelProperty>>() {
                @Override
                List<ModelProperty> apply(ResolvedField input) {
                    if (!givenContext.canIgnore(input.getType())) {
                        if (memberIsUnwrapped(jacksonProperty.getField())) {
                            return propertiesFor(
                                    input.getType(),
                                    ModelContext.fromParent(givenContext, input.getType()),
                                    String.format(
                                            "%s%s",
                                            namePrefix,
                                            unwrappedPrefix(jacksonProperty.getPrimaryMember())))
                        }
                        return newArrayList(fieldModelProperty(input, jacksonProperty, givenContext, namePrefix))
                    }
                    return newArrayList()
                }
            }
        }

        private List<ModelProperty> candidateProperties(
                ResolvedType type,
                AnnotatedMember member,
                BeanPropertyDefinition jacksonProperty,
                ModelContext givenContext,
                String namePrefix) {

            List<ModelProperty> properties = newArrayList()
            if (member instanceof AnnotatedMethod) {
                properties.addAll(findAccessorMethod(type, member)
                        .transform(propertyFromBean(givenContext, jacksonProperty, namePrefix))
                        .or(new ArrayList<ModelProperty>()))
            } else if (member instanceof AnnotatedField) {
                properties.addAll(findField(type, jacksonProperty.getInternalName())
                        .transform(propertyFromField(givenContext, jacksonProperty, namePrefix))
                        .or(new ArrayList<ModelProperty>()))
            } else if (member instanceof AnnotatedParameter) {
                ModelContext modelContext = ModelContext.fromParent(givenContext, type)
                properties.addAll(
                        fromFactoryMethod(
                                type,
                                jacksonProperty,
                                (AnnotatedParameter) member,
                                modelContext,
                                namePrefix))
            }
            return from(properties).filter(hiddenProperties()).toList()

        }

        private Predicate<? super ModelProperty> hiddenProperties() {
            return new Predicate<ModelProperty>() {
                @Override
                boolean apply(ModelProperty input) {
                    return !input.isHidden()
                }
            }
        }

        private Optional<ResolvedField> findField(
                ResolvedType resolvedType,
                final String fieldName) {

            return tryFind(fields.in(resolvedType), new Predicate<ResolvedField>() {
                boolean apply(ResolvedField input) {
                    return fieldName == input.getName()
                }
            })
        }

        private ModelProperty fieldModelProperty(
                ResolvedField childField,
                BeanPropertyDefinition jacksonProperty,
                ModelContext modelContext,
                String namePrefix) {

            String fieldName = name(
                    jacksonProperty,
                    modelContext.isReturnType(),
                    namingStrategy,
                    namePrefix)

            FieldModelProperty fieldModelProperty =
                    new FieldModelProperty(
                            fieldName,
                            childField,
                            typeResolver,
                            modelContext.getAlternateTypeProvider(),
                            jacksonProperty)

            ModelPropertyBuilder propertyBuilder = new ModelPropertyBuilder()
                    .name(fieldModelProperty.getName())
                    .type(fieldModelProperty.getType())
                    .qualifiedType(fieldModelProperty.qualifiedTypeName())
                    .position(fieldModelProperty.position())
                    .required(fieldModelProperty.isRequired())
                    .description(fieldModelProperty.propertyDescription())
                    .allowableValues(fieldModelProperty.allowableValues())
                    .example(fieldModelProperty.example())
            return schemaPluginsManager.property(
                    new ModelPropertyContext(propertyBuilder,
                            childField.getRawMember(),
                            typeResolver,
                            modelContext.getDocumentationType()))
                    .updateModelRef(modelRefFactory(modelContext, typeNameExtractor))
        }

        private ModelProperty beanModelProperty(
                ResolvedMethod childProperty,
                BeanPropertyDefinition jacksonProperty,
                ModelContext modelContext,
                String namePrefix) {

            String propertyName = name(
                    jacksonProperty,
                    modelContext.isReturnType(),
                    namingStrategy,
                    namePrefix)

            BeanModelProperty beanModelProperty = new BeanModelProperty(
                    propertyName,
                    childProperty,
                    typeResolver,
                    modelContext.getAlternateTypeProvider(),
                    jacksonProperty)

            LOG.debug("Adding property {} to model", propertyName)
            ModelPropertyBuilder propertyBuilder = new ModelPropertyBuilder()
                    .name(beanModelProperty.getName())
                    .type(beanModelProperty.getType())
                    .qualifiedType(beanModelProperty.qualifiedTypeName())
                    .position(beanModelProperty.position())
                    .required(beanModelProperty.isRequired())
                    .isHidden(false)
                    .description(beanModelProperty.propertyDescription())
                    .allowableValues(beanModelProperty.allowableValues())
                    .example(beanModelProperty.example())
            return schemaPluginsManager.property(
                    new ModelPropertyContext(propertyBuilder,
                            jacksonProperty,
                            typeResolver,
                            modelContext.getDocumentationType()))
                    .updateModelRef(modelRefFactory(modelContext, typeNameExtractor))
        }

        private ModelProperty paramModelProperty(
                ResolvedParameterizedMember constructor,
                BeanPropertyDefinition jacksonProperty,
                AnnotatedParameter parameter,
                ModelContext modelContext,
                String namePrefix) {

            String propertyName = name(
                    jacksonProperty,
                    modelContext.isReturnType(),
                    namingStrategy,
                    namePrefix)

            ParameterModelProperty parameterModelProperty = new ParameterModelProperty(
                    propertyName,
                    parameter,
                    constructor,
                    typeResolver,
                    modelContext.getAlternateTypeProvider(),
                    jacksonProperty)

            LOG.debug("Adding property {} to model", propertyName)
            ModelPropertyBuilder propertyBuilder = new ModelPropertyBuilder()
                    .name(parameterModelProperty.getName())
                    .type(parameterModelProperty.getType())
                    .qualifiedType(parameterModelProperty.qualifiedTypeName())
                    .position(parameterModelProperty.position())
                    .required(parameterModelProperty.isRequired())
                    .isHidden(false)
                    .description(parameterModelProperty.propertyDescription())
                    .allowableValues(parameterModelProperty.allowableValues())
                    .example(parameterModelProperty.example())
            return schemaPluginsManager.property(
                    new ModelPropertyContext(propertyBuilder,
                            jacksonProperty,
                            typeResolver,
                            modelContext.getDocumentationType()))
                    .updateModelRef(modelRefFactory(modelContext, typeNameExtractor))
        }

        private Optional<ResolvedMethod> findAccessorMethod(ResolvedType resolvedType, final AnnotatedMember member) {
            return tryFind(accessors.in(resolvedType), new Predicate<ResolvedMethod>() {
                boolean apply(ResolvedMethod accessorMethod) {
                    SimpleMethodSignatureEquality methodComparer = new SimpleMethodSignatureEquality()
                    return methodComparer.equivalent(accessorMethod.getRawMember(), (Method) member.getMember())
                }
            })
        }

        private List<ModelProperty> fromFactoryMethod(
                final ResolvedType resolvedType,
                final BeanPropertyDefinition beanProperty,
                final AnnotatedParameter member,
                final ModelContext givenContext,
                final String namePrefix) {

            Optional<ModelProperty> property = factoryMethods.in(resolvedType, factoryMethodOf(member))
                    .transform(new Function<ResolvedParameterizedMember, ModelProperty>() {
                @Override
                ModelProperty apply(ResolvedParameterizedMember input) {
                    return paramModelProperty(input, beanProperty, member, givenContext, namePrefix)
                }
            })
            if (property.isPresent()) {
                return newArrayList(property.get())
            }
            return newArrayList()
        }

        Predicate<ResolvedParameterizedMember> factoryMethodOf(final AnnotatedParameter parameter) {
            return new Predicate<ResolvedParameterizedMember>() {
                @Override
                boolean apply(ResolvedParameterizedMember input) {
                    return input.getRawMember() == parameter.getOwner().getMember()
                }
            }
        }

        private BeanDescription beanDescription(ResolvedType type, ModelContext context) {
            if (context.isReturnType()) {
                SerializationConfig serializationConfig = objectMapper.getSerializationConfig()
                return serializationConfig.introspect(serializationConfig.constructType(type.getErasedType()))
            } else {
                DeserializationConfig serializationConfig = objectMapper.getDeserializationConfig()
                return serializationConfig.introspect(serializationConfig.constructType(type.getErasedType()))
            }
        }
    }

    private static class SimpleMethodSignatureEquality extends Equivalence<Method> {

        @Override
        protected boolean doEquivalent(Method first, Method other) {
            return first.getName() == other.getName() && first.getReturnType() == other.getReturnType() && equalParamTypes(first.getParameterTypes(), other.getParameterTypes());
        }

        private boolean equalParamTypes(Class<?>[] params1, Class<?>[] params2) {
            if (params1.length == params2.length) {
                for (int i = 0; i < params1.length; i++) {
                    if (params1[i] != params2[i]) {
                        return false;
                    }
                }
                return true;
            }
            return false;
        }

        @Override
        protected int doHash(Method method) {
            return method.hashCode();
        }
    }

}
