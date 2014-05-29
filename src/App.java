import java.io.*;
import java.util.*;

public class App {

	public static void main(String[] args) throws IOException {
		Random rand = new Random();
		rand.setSeed(1);
		int nfolds = 10;
		int classes = 5;
		int steps = 20;
		int normalise = 15;
		
		
		Vector<Rating> ratings = new Vector<Rating>();

        String filesrc = System.getProperty("user.dir")+"/ml-100k";

		//BufferedReader br = new BufferedReader(new FileReader("ml-100K/u.data"));
        System.out.println("Reading Ratings");
        BufferedReader br = new BufferedReader(new FileReader(filesrc+"/u.data"));
		while (br.ready()){
			Scanner tk = new Scanner(br.readLine());
			tk.useDelimiter("::");
			ratings.add(new Rating(tk.nextInt(), tk.nextInt(),tk.nextInt(),tk.nextInt(),rand.nextInt(nfolds)));
		}
		br.close();
        System.out.println("Finished Reading Ratings");


        PLSADoubles pdubs  = new PLSADoubles(steps,classes);
        NormalisationModel normaliseMod = new NormalisationModel(pdubs, normalise);




		//CVReport cv = CVRunner.run(new NormalisationModel(new SlopeOne(), 3), ratings, 10);
		//CVReport cv = CVRunner.run(new NormalisationModel(new GuessZero(), 3), ratings, 10);
		//CVReport cv = CVRunner.run(new GuessZero(), ratings, 10);
		//CVReport cv = CVRunner.run(new SlopeOne(), ratings, 10);
		//CVReport cv = CVRunner.run(new RandomModel(), ratings, 10);
		CVReport cv = CVRunner.run(new NormalisationModel(new PLSADoubles(steps, classes), normalise), ratings, nfolds);


		System.out.printf("Mean: %f,  SD: %f\n", cv.MSE, cv.var);
		
	}

}
