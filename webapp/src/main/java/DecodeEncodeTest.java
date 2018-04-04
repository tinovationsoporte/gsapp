import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class DecodeEncodeTest {

	public static void main(String ...args){
		
		try {

			String url = "http://gestionsocial.cloudapp.net/imagenesservlet?imageId=5";

			String encodedUrl = URLEncoder.encode(url, "UTF-8");

			System.out.println("Encoded URL " + encodedUrl);

			String decodedUrl = URLDecoder.decode(url, "UTF-8");

			System.out.println("Dncoded URL " + decodedUrl);

		 } catch (UnsupportedEncodingException e) {

				System.err.println(e);

		 }
	}
		
}
