package com.lq186.demos.sshd;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.Collections;

import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.channel.ChannelExec;
import org.apache.sshd.client.channel.ClientChannel;
import org.apache.sshd.client.channel.ClientChannelEvent;
import org.apache.sshd.client.config.hosts.HostConfigEntry;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.client.subsystem.sftp.SftpClient;
import org.apache.sshd.client.subsystem.sftp.SftpClient.DirEntry;
import org.apache.sshd.client.subsystem.sftp.SftpClientFactory;
import org.apache.sshd.common.util.security.SecurityUtils;

public class SimpleDemo {

	public static void main(String[] args) {
		
	}
	
	public static void simpleTest() {
		
	}
	
	public static void execCmd(HostConfigEntry hostConfig, String password, String cmd) throws IOException {
		
		SshClient client = SshClient.setUpDefaultClient();
		client.start();
		
		ClientSession session = client.connect(hostConfig).getSession();
		session.addPasswordIdentity(password);
		
		if (!session.auth().isSuccess()) {
			System.out.println("Auth failed.");
			return;
		}
		
		ChannelExec exec = session.createExecChannel(cmd);
		exec.setOut(System.out);
		exec.open();
		exec.waitFor(Collections.singleton(ClientChannelEvent.CLOSED), 0);
		exec.close();
		
		client.stop();
	}
	
	public static void sftpTest(HostConfigEntry hostConfig, String password) throws IOException {
		
		Path localPath = Paths.get("local_sshd.txt");
		Files.deleteIfExists(localPath);
		Files.createFile(localPath);
		Files.write(localPath, "This is sshd demo file content.".getBytes());
		
		SshClient client = SshClient.setUpDefaultClient();
		client.start();
		
		ClientSession session = client.connect(hostConfig).getSession();
		session.addPasswordIdentity(password);
		// session.addPublicKeyIdentity(SecurityUtils.loadKeyPairIdentity("keyname", new FileInputStream("keyfile.pem"), null));
		
		if (!session.auth().isSuccess()) {
			System.out.println("Auth failed.");
			return;
		}
		
		SftpClient sftp = SftpClientFactory.instance().createSftpClient(session);
		
		sftp.readDir(".").forEach(dir -> {
			System.out.println(MessageFormat.format("name: {0}\t\t type: {1}", dir.getFilename(), dir.getAttributes().getType()));
		});
		
		String remotePath = "test/remote_sshd.txt";
		try (OutputStream out = sftp.write(remotePath);) {
			Files.copy(localPath, out);
		}
		
		try (InputStream in = sftp.read(remotePath);) {
			Path destLocalPath = Paths.get("local_from_remote_sshd.txt");
			Files.deleteIfExists(destLocalPath);
			Files.copy(in, destLocalPath);
		}
		
		sftp.close();
		client.stop();
	}
}
