package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import bean.*;

public class UserAccount {

	private Connection connection;

	public UserAccount(Connection connection) {
		this.connection = connection;
	}

	public void insert(String username, String password, String name, String surname, String email, String phone,
			String role) throws SQLException {

		String sql = "insert into user (username, password, name, surname, email, phone, role) values (?, ?, ?, ?, ?, ?, ?)";

		PreparedStatement statement = connection.prepareStatement(sql);

		statement.setString(1, username);
		statement.setString(2, password);
		statement.setString(3, name);
		statement.setString(4, surname);
		statement.setString(5, email);
		statement.setString(6, phone);
		statement.setString(7, role);

		statement.executeUpdate();

		statement.close();
	}

	public boolean login(String username, String password) throws SQLException {

		String sql = "select count(*) as count from user where username=? and password=?";
		int count = 0;

		PreparedStatement statement = connection.prepareStatement(sql);

		statement.setString(1, username);
		statement.setString(2, password);

		ResultSet rs = statement.executeQuery();

		if (rs.next()) {
			count = rs.getInt("count");
		}

		rs.close();
		statement.close();

		if (count == 0) {
			return false;
		} else {
			return true;
		}
	}

	public boolean exists(String username) throws SQLException {

		String sql = "select count(*) as count from user where username=?";

		PreparedStatement statement = connection.prepareStatement(sql);

		statement.setString(1, username);

		ResultSet rs = statement.executeQuery();

		int count = 0;

		if (rs.next()) {
			count = rs.getInt("count");
		}

		rs.close();
		statement.close();

		if (count == 0) {
			return false;
		} else {
			return true;
		}
	}

	public String getRole(String username) throws SQLException {
		String sql = "select role from user where username=?";
		String role = "";

		PreparedStatement statement = connection.prepareStatement(sql);

		statement.setString(1, username);
		ResultSet rs = statement.executeQuery();

		if (rs.next()) {
			role = rs.getString(1);

		}
		rs.close();
		statement.close();

		return role;
	}

	public List<Listing> getResults(String checkin, String checkout, String people) throws SQLException {
		String sql = "select id, thumbnail_url, listing.price as price, room_type, beds, number_of_reviews, review_scores_rating, accommodates, (select floor(replace(STRIP_NON_DIGIT(price),',','.')) as final) as finals, "
				+ "(select count(available) from schedule where schedule.listing_id=listing.id and available='f' and date between ? and ?) as count "
				+ "from listing where exists(select 1 from schedule where date=?) and exists (select 1 from schedule where date=?) having count=0 and accommodates>=? order by finals asc";

		PreparedStatement statement = null;
		ResultSet rs = null;
		List<Listing> listings = new ArrayList<Listing>();

		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, checkin);
			statement.setString(2, checkout);
			statement.setString(3, checkin);
			statement.setString(4, checkout);
			statement.setString(5, people);

			rs = statement.executeQuery();

			while (rs.next()) {
				Listing listing = new Listing();
				listing.setId(rs.getInt("id"));
				listing.setThumbnailUrl(rs.getString("thumbnail_url"));
				listing.setPrice(rs.getString("price"));
				listing.setRoomType(rs.getString("room_type"));
				listing.setBeds(rs.getInt("beds"));
				listing.setNumberOfReviews(rs.getInt("number_of_reviews"));
				listing.setReviewScoresRating(rs.getInt("review_scores_rating"));

				listings.add(listing);
			}

		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ignore) {
				}
			if (statement != null)
				try {
					statement.close();
				} catch (SQLException ignore) {
				}
		}
		return listings;
	}

	public Listing getCharacteristics(String id) throws SQLException {

		int int_id = Integer.parseInt(id);

		String sql = "select id, picture_url, beds, bathrooms, room_type, bedrooms, square_feet, description, amenities, minimum_nights, "
				+ "latitude, longitude, street, neighbourhood_cleansed, transit, host_name, host_thumbnail_url from listing where id=?";

		PreparedStatement statement = null;
		ResultSet rs = null;

		Listing listing = new Listing();

		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, int_id);

			rs = statement.executeQuery();

			while (rs.next()) {
				listing.setId(rs.getInt("id"));
				listing.setPictureUrl(rs.getString("picture_url"));
				listing.setBeds(rs.getInt("beds"));
				listing.setBathrooms(rs.getInt("bathrooms"));
				listing.setRoomType(rs.getString("room_type"));
				listing.setBedrooms(rs.getInt("bedrooms"));
				listing.setSquareFeet(rs.getString("square_feet"));
				listing.setDescription(rs.getString("description"));
				listing.setAmenities(rs.getString("amenities"));
				listing.setMinimumNights(rs.getInt("minimum_nights"));
				listing.setLatitude(rs.getDouble("latitude"));
				listing.setLongitude(rs.getDouble("longitude"));
				listing.setStreet(rs.getString("street"));
				listing.setNeighbourhoodCleansed(rs.getString("neighbourhood_cleansed"));
				listing.setTransit(rs.getString("transit"));
				listing.setHostName(rs.getString("host_name"));
				listing.setHostThumbnailUrl(rs.getString("host_thumbnail_url"));
			}

		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ignore) {
				}
			if (statement != null)
				try {
					statement.close();
				} catch (SQLException ignore) {
				}
		}
		return listing;
	}
	
	public void book(String username, String checkin, String checkout, String guests, String listing_id) throws SQLException{
		String sql = "insert into user_booking (checkin, checkout, guests, listing_id, username) values (?, ?, ?, ?, ?)";

		PreparedStatement statement = connection.prepareStatement(sql);

		statement.setString(1, checkin);
		statement.setString(2, checkout);
		statement.setString(3, guests);
		statement.setString(4, listing_id);
		statement.setString(5, username);

		statement.executeUpdate();

		statement.close();
		
	}
	
	
	public List<User> getList() throws SQLException {

		String sql = "select * from user";
		PreparedStatement statement = null;
		ResultSet rs = null;
		List<User> users = new ArrayList<User>();

		try {
			statement = connection.prepareStatement(sql);
			rs = statement.executeQuery();

			while (rs.next()) {
				User user = new User();
				user.setUsername(rs.getString("Username"));
				user.setPassword(rs.getString("Password"));
				user.setName(rs.getString("Name"));
				user.setSurname(rs.getString("Surname"));
				user.setEmail(rs.getString("Email"));
				user.setPhone(rs.getString("Phone"));
				user.setRole(rs.getString("Role"));

				users.add(user);
			}
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ignore) {
				}
			if (statement != null)
				try {
					statement.close();
				} catch (SQLException ignore) {
				}
		}

		return users;
	}

	public void userConfirm(String username) throws SQLException {

		String sql = "update user set role='Host' where username=?";
		PreparedStatement statement = connection.prepareStatement(sql);

		statement.setString(1, username);
		statement.executeUpdate();

		statement.close();
	}

	public User getUser(String username) throws SQLException {

		String sql = "select * from user where username=?";

		PreparedStatement statement = null;
		ResultSet rs = null;

		User user = new User();

		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, username);

			rs = statement.executeQuery();

			while (rs.next()) {
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setName(rs.getString("name"));
				user.setSurname(rs.getString("surname"));
				user.setEmail(rs.getString("email"));
				user.setPhone(rs.getString("phone"));
				user.setRole(rs.getString("role"));
			}

		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ignore) {
				}
			if (statement != null)
				try {
					statement.close();
				} catch (SQLException ignore) {
				}
		}
		return user;
	}

	public void updateUser(String username, String password, String name, String surname, String email, String phone)
			throws SQLException {
		String sql1 = "update user set password=? where username=?";
		String sql2 = "update user set name=? where username=?";
		String sql3 = "update user set surname=? where username=?";
		String sql4 = "update user set email=? where username=?";
		String sql5 = "update user set phone=? where username=?";

		if (password != "") {
			PreparedStatement statement = connection.prepareStatement(sql1);

			statement.setString(1, password);
			statement.setString(2, username);
			statement.executeUpdate();

			statement.close();
		}
		if (name != "") {
			PreparedStatement statement = connection.prepareStatement(sql2);

			statement.setString(1, name);
			statement.setString(2, username);
			statement.executeUpdate();

			statement.close();
		}
		if (surname != "") {
			PreparedStatement statement = connection.prepareStatement(sql3);

			statement.setString(1, surname);
			statement.setString(2, username);
			statement.executeUpdate();

			statement.close();
		}
		if (email != "") {
			PreparedStatement statement = connection.prepareStatement(sql4);

			statement.setString(1, email);
			statement.setString(2, username);
			statement.executeUpdate();

			statement.close();
		}
		if (phone != "") {
			PreparedStatement statement = connection.prepareStatement(sql5);

			statement.setString(1, phone);
			statement.setString(2, username);
			statement.executeUpdate();

			statement.close();
		}

	}
}
