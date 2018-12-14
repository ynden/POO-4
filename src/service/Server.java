package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import filter.AbstractFilter;

public class Server implements Runnable {
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private List<Character> dataList;
	private static Server server;
	private InputStream is;
	private OutputStream os;
	private Map<String, AbstractFilter> filters;
	private Service service;

	public static Server getInstance() {
		if (server == null)
			server = new Server();

		return server;

	}

	protected Server() {
	}

	public void addToList(Character character) {
		dataList.add(character);
	}

	public static void main(String[] args) {
		Server.getInstance();
	}

	public Map<String, AbstractFilter> getFilters() {
		return filters;
	}

	public void setFilters(Map<String, AbstractFilter> filters) {
		this.filters = filters;
	}

	@Override
	public void run() {
		try {
			serverSocket = new ServerSocket(32745);
			dataList = new ArrayList<Character>();

			while (true) {
				System.out.println("Waiting for connections");

				clientSocket = serverSocket.accept();
				System.out.println("Connection established!");

				is = clientSocket.getInputStream();
				BufferedReader bf = new BufferedReader(new InputStreamReader(is));

				String request = bf.readLine();
				// String requestedSID = request.split(":")[2];

				// service = new Service1(this.filters, request);
				service = new Service2(filters);

				String response = service.execute();
				System.out.println(response.split("\n")[2]);

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
