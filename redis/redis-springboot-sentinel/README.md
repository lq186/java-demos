### Redis 哨兵模式示例
* redis 配置(redis.conf)
```bash
# 设置 Reids 的密码
requirepass "lq186_com"

# 设置为主节点(127.0.0.1:6379)的从节点
slaveof 127.0.0.1 6379

# Redis 主节点的密码
masterauth "sc_baas_123"
```
`启动Redis服务: redis-service redis.conf`

* sentinel 配置(sentinel.conf)

```bash
# prot 当前Sentinel服务运行的端口
port 26379

# Sentinel 监视一个名为 master 的主redis实例,
# 这个实例的IP地址为: 127.0.0.1, 端口为: 6379;
# 判定这个主实例失效至少需要 2 个Sentinel进程的同意
# 只要同意的Sentinel进程不达标, 自动failover就不会执行
sentinel monitor master 127.0.0.1 6379 2

# 指定Sentinel认为Redis实例失效所需的毫秒数
sentinel down-after-milliseconds master 5000

# 指定在执行故障转移时, 最多可以有多少个从redis实例在同步新的主实例
# 在从redis实例较多的情况下, 这个数字越小, 同步时间越长, 完成故障转移所需的时间就越长
sentinel parallel-syncs master 1

# 如果在该时间(ms)内没能完成failover操作, 则认为该 failover 失败
sentinel failover-timeout master 15000

# 主redis密码
sentinel auth-pass master "lq186_com"

```