package com.wit.xzy.client;

import com.wit.xzy.entity.RpcRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @Author ZongYou
 **/
public class RpcClient {

    private static final Logger logger = LoggerFactory.getLogger(RpcClient.class);

    public Object sendRequest(RpcRequest rpcRequest, String host, int port) {
        /**
         * C/S的通信模式中，客户端需要主动构造与服务器连接的Socket
         * socket套接字实现TCP网络传输
         * try()中一般放对资源的申请，若{}出现异常，()资源会自动关闭
         */
        try (Socket socket = new Socket(host, port)) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream.writeObject(rpcRequest);
            objectOutputStream.flush();
            return objectInputStream.readObject();//读取输出流的数据
        } catch (IOException | ClassNotFoundException e) {
            logger.error("调用时有错误发生：" + e);
            return null;
        }
    }
}
