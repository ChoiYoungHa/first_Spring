package hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class FileCreate {

	public static void main(String[] args) throws Exception {
		
		//인자값이 2개 아니라면.
		
		if(args.length !=2) {
			System.out.println("");
			System.exit(2);
		}
		
		try {
			Configuration conf = new Configuration();
			FileSystem hdfs = FileSystem.get(conf);
			
			Path path = new Path(args[0]);
			if(hdfs.exists(path)) {
				hdfs.delete(path, true);
			}
			
			FSDataOutputStream outStream = hdfs.create(path);
			outStream.writeUTF(args[1]);
			outStream.close();
			
			FSDataInputStream inputStream = hdfs.open(path);
			String inputString = inputStream.readUTF();
			inputStream.close();
			
			System.out.println("## inputString :" + inputString);
			
			hdfs.close();
		} catch(Exception e) {
			System.out.println(e.toString());
		}
		

	}

}
