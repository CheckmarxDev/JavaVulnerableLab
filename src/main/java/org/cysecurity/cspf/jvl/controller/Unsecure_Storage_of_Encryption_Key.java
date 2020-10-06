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
				
				Regions clientRegion = Regions.DEFAULT_REGION;
				String bucketName = "S3B_541";
				String stringObjKeyName = "XKIB5WDJHVGINH8YOZFC";        
            
				AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(clientRegion)
                    .build();
			

				GetObjectRequest fullObject = s3Client.getObject(new GetObjectRequest(bucketName, stringObjKeyName));
				
				privateKey = readPrivateKey(fullObject.getObjectContent());
				Cipher decrypt = Cipher.getInstance("RSA");
				decrypt.init(Cipher.DECRYPT_MODE, privateKey);
				byte[] decryptedMessage=decrypt.doFinal(svrs.getMessage());
				
				DoTask(decryptedMessage);

				headerJson.put("x-custom-header");
		 
				responseJson.put("statusCode", 200);
				responseJson.put("headers", headerJson);
				responseJson.put("body", "Success");
			}	 
		} catch (ParseException pex) {
			responseJson.put("statusCode", 400);
			responseJson.put("exception", pex);
		}
	 
		OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
		writer.write(responseJson.toString());
		writer.close();
	}	
	
	public RSAPrivateKey readPrivateKey(String key) throws Exception {
	 
		String privateKeyPEM = key
		  .replace("-----BEGIN PRIVATE KEY-----", "")
		  .replaceAll(System.lineSeparator(), "")
		  .replace("-----END PRIVATE KEY-----", "");
	 
		byte[] encoded = Base64.decodeBase64(privateKeyPEM);
	 
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
		return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
	}
	
	private static String readTextInputStream(InputStream input) throws IOException {
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        return reader.readAll();
    }
}