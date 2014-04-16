package lab.cgcl.aliBigdata.BPNNlearning.CF;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class CollaborationFilter
{

	public static void main(String[] args) throws Exception
	{
		// user id
		int UID = 1; 

		// Initialization
		String rating = "ratings.dat";
		String movieInfo = "movies.dat";
		int N_users = 6040, N_movies = 3952;

		Integer[][] U_matrix = new Integer[N_users + 1][N_movies + 1];

		// reading files
		try
		{
			FileInputStream stream = new FileInputStream(rating);
			DataInputStream in = new DataInputStream(stream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			while ((strLine = br.readLine()) != null)
			{
				ParseAndInsert(strLine, U_matrix);
			}
			in.close();
		} catch (Exception e)
		{
			System.err.println("Error: " + e.getMessage());
		}

		HashMap<Integer, String> filmsHashMap = new HashMap<Integer, String>();
		try
		{
			FileInputStream stream = new FileInputStream(movieInfo);
			DataInputStream in = new DataInputStream(stream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			while ((strLine = br.readLine()) != null)
			{
				ParseMovies(strLine, filmsHashMap);
			}
			in.close();
		} catch (Exception e)
		{
			System.err.println("Error: " + e.getMessage());
		}

		// rate every unrated user item
		ArrayList<Item> items = new ArrayList<Item>();
		for (int i = 0; i <= N_movies; i++)
		{
			if (U_matrix[UID][i] == null)
			{
				double rate = getItemRate(U_matrix, UID, i);
				if (Double.isNaN(rate))
				{
					continue;
				}

				if (filmsHashMap.containsKey(i))
				{
					items.add(new Item(i, filmsHashMap.get(i), rate));
				} else
				{
					items.add(new Item(i, rate));
				}
			}
		}

		// sort and print top n items.
		Collections.sort(items);
		System.out.println("Top 10:");
		PrintTop(items, 10);

		System.out.println("Worst 10:");
		// reverse sort and print worst n items.
		Collections.reverse(items);
		PrintTop(items, 10);
	}
	
	
	/*this function returns the weighted rate from user within top 30 most similar ones */

	public static double getItemRate(Integer[][] matrix, int userID,
			int itemID) throws Exception
	{
		ArrayList<Integer[]> users = new ArrayList<Integer[]>();
		for (int i = 0; i < matrix.length; i++)
		{
			if (matrix[i][itemID] != null)
			{
				users.add(matrix[i]);
			}
		}

		ArrayList<WeightedUser> weightedUsers = new ArrayList<WeightedUser>();
		for (Integer[] user : users)
		{
			Integer[][] vecs = CutVectors(user, matrix[userID]);
			double similarity = VectorSimilarity.Pearson(vecs[0], vecs[1]);
			if (Double.isNaN(similarity))
			{
				continue;
			}
			weightedUsers.add(new WeightedUser(user, similarity));
		}

		// get top 30 users
		int max = 30;
		if (max > weightedUsers.size())
			max = weightedUsers.size();
		Collections.sort(weightedUsers);
		List<WeightedUser> wu = weightedUsers.subList(0, max);

		double rate = weightedRate(matrix[userID], wu, itemID);
		return rate;
	}
	
/*This function calculate the weighted rate for the un-rated movie*/
	public static double weightedRate(Integer[] userRatings,
			List<WeightedUser> weightedUsers, int itemID)
	{
		double weightUsersSum = 0;
		double weightSum = 0;
		for (WeightedUser user : weightedUsers)
		{
			double avg = getAvg(user.ratings);
			weightUsersSum += (user.ratings[itemID] - avg) * user.weight;
			weightSum += Math.abs(user.weight);
		}

		double userAvg = getAvg(userRatings);
		double rate= userAvg+ (weightUsersSum / weightSum);
		return rate;
	}

		public static double getAvg(Integer[] vector)
		{
			double avg = 0;
			int count=0;
			for (int i = 0; i < vector.length; i++)
			{
				if (vector[i] != null)
				{
					avg += vector[i];
					count++;
				}
			}
			return avg / count;
		}
		
/*This function returns the vectors that contain the movie rated by both users*/
		public static Integer[][] CutVectors(Integer[] user1, Integer[] user2)
				throws Exception
		{
			if (user1.length != user2.length)
			{
				throw new Exception("user1.length != user2.length");
			}

			ArrayList<Integer> list1 = new ArrayList<Integer>();
			ArrayList<Integer> list2 = new ArrayList<Integer>();
			for (int i = 0; i < user1.length; i++)
			{
				if (user1[i] != null && user2[i] != null)
				{
					list1.add(user1[i]);
					list2.add(user2[i]);
				}
			}

			Integer[][] result = new Integer[2][];
			result[0] = (Integer[]) list1.toArray(new Integer[list1.size()]);
			result[1] = (Integer[]) list2.toArray(new Integer[list1.size()]);
			return result;
		}
	

	public static class WeightedUser implements Comparable<WeightedUser>
	{
		Integer[] ratings;
		double weight;

		public WeightedUser(Integer[] ratings, double weight)
		{
			this.ratings = ratings;
			this.weight = weight;
		}

		public int compareTo(WeightedUser u)
		{
			if (weight > u.weight)
			{
				return -1;
			} else if (weight == u.weight)
			{
				return 0;
			} else
			{
				return 1;
			}
		}
	}

	private static void ParseAndInsert(String strLine, Integer[][] matrix)
	{
		String[] tokens = strLine.split("::");
		int UID = Integer.parseInt(tokens[0]);
		int movieID = Integer.parseInt(tokens[1]);
		int rating = Integer.parseInt(tokens[2]);
		matrix[UID][movieID] = rating;
	}

	private static void ParseMovies(String strLine,
			HashMap<Integer, String> filmsHashMap)
	{
		String[] tokens = strLine.split("::");
		int movieID = Integer.parseInt(tokens[0]);
		String title = tokens[1];
		filmsHashMap.put(movieID, title);
	}

	private static void PrintTop(ArrayList<Item> items, int n)
	{
		for (int i = 0; i < n; i++)
		{
			System.out.println(items.get(i).getId() + ": "
					+ items.get(i).getTitle() + " rate: "
					+ items.get(i).getRating());
		}
	}

	public static class Item implements Comparable<Item>
	{

		private int id;

		private double rating;

		private String title;

		public Item(int id, double rating)
		{
			this.id = id;
			this.rating = rating;
			this.title = "";
		}

		public Item(int id, String title, double rating)
		{
			this.id = id;
			this.title = title;
			this.rating = rating;
		}

		public int getId()
		{
			return id;
		}

		public String getTitle()
		{
			return title;
		}

		public double getRating()
		{
			return rating;
		}

		public int compareTo(Item t)
		{
			if (rating > t.rating)
			{
				return -1;
			} else if (rating == t.rating)
			{
				return 0;
			} else
			{
				return 1;
			}
		}
	}
}
