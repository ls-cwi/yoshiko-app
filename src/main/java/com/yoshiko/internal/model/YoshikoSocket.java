package com.yoshiko.internal.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;

public class YoshikoSocket {
	private Socket socket = null;
	private InputStream din = null;
	private OutputStream dout = null;
	private int data_type = 0;
	private byte[] data_payload = null;

	public YoshikoSocket(String host, int port) throws UnknownHostException, IOException {
		try {
			socket = new Socket(host, port);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			throw new UnknownHostException("Socket UnknownHostException"+e.getMessage());
			//e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			throw new IOException("Socket IOException"+e.getMessage());
		}
		try {
			din = socket.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			throw new IOException("Socket getInputStream"+e.getMessage());
		}
		try {
			dout = socket.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			throw new IOException("Socket getOutputStream"+e.getMessage());
		}
	}

	
	public void get_receive() throws IOException {
		DataInputStream dataStream = new DataInputStream(din);
		// read the type byte
		int type = dataStream.read();
		// read four bytes in big-endian byte order (network byte order)
		// and interpret them as an int, the length of the payload
		int payloadLength = dataStream.readInt();
		// create a byte array to store the payload
		byte[] payload = new byte[payloadLength];
		// how many bytes of the payload have been read so far
		int payloadBytesRead = 0;
		// until all expected bytes are read
		while (payloadBytesRead < payloadLength) {
			// read up to `payloadLength - payloadBytesRead` bytes into
			// the byte array, starting at `payload[payloadBytesRead]`. In
			// other words, read the next part of the payload.
			int newBytesRead = dataStream.read(
					payload,
					payloadBytesRead,
					payloadLength - payloadBytesRead);
			// if the end of the stream was encountered while reading
			if (newBytesRead == -1) {
				throw new EOFException("Incomplete response from server.");
			}
			payloadBytesRead += newBytesRead;
		}
		data_type = type;
		data_payload = payload;
	}

	public boolean SendCommand(int type, String name, String payload)
			throws IOException {
		DataOutputStream dataStream = new DataOutputStream(dout);
		dataStream.write((byte) type);
		if(name != null) {
			byte[] namedata = name.getBytes(Charset.forName("US-ASCII"));
			dataStream.writeInt(namedata.length);
			dataStream.write(namedata);
		} else {
			dataStream.writeInt(0);
		}
		if(payload != null) {
			byte[] payloaddata = payload.getBytes(Charset.forName("US-ASCII"));
			dataStream.writeInt(payloaddata.length);
			dataStream.write(payloaddata);
		} else {
			dataStream.writeInt(0);
		}
		dataStream.flush();
		data_type = 0;
		get_receive();
		if (data_type == 8)
			return true;
		else
			return false;
	}

	public String getOutData() {
		return new String(data_payload);
	}
}
