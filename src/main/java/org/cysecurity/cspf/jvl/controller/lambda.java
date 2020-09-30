public class MyHandler implements RequestHandler<Map<String,String>, String> {
	
	public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context)
									throws IOException {
	 
		JSONParser parser = new JSONParser();
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		JSONObject responseJson = new JSONObject();
	 
		try {
			JSONObject event = (JSONObject) parser.parse(reader);
	 
			if (event.get("body") != null) {
				MyRemoteService svrs = new MyRemoteService((String) event.get("body"));				
				Client client = ClientBuilder.newClient();

				WebTarget resource = client.target(svrs.getURL());

				Builder request = resource.request();
				request.accept(MediaType.APPLICATION_JSON);

				Response response = request.get();
				
				JSONObject responseBody = new JSONObject();
				responseBody.put("message", response.getEntity());
		 
				JSONObject headerJson = new JSONObject();
				headerJson.put("x-custom-header");
		 
				responseJson.put("statusCode", response.getStatus());
				responseJson.put("headers", headerJson);
				responseJson.put("body", responseBody.toString());
			}	 
		} catch (ParseException pex) {
			responseJson.put("statusCode", 400);
			responseJson.put("exception", pex);
		}
	 
		OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
		writer.write(responseJson.toString());
		writer.close();
	}	
}