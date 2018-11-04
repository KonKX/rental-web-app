package bean;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the user_booking database table.
 * 
 */
@Entity
@Table(name="user_booking")
@NamedQuery(name="UserBooking.findAll", query="SELECT u FROM UserBooking u")
public class UserBooking implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String checkin;

	private String checkout;

	private String guests;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="username")
	private User user;

	//bi-directional one-to-one association to Listing
	@OneToOne(mappedBy="userBooking")
	private Listing listing;

	public UserBooking() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCheckin() {
		return this.checkin;
	}

	public void setCheckin(String checkin) {
		this.checkin = checkin;
	}

	public String getCheckout() {
		return this.checkout;
	}

	public void setCheckout(String checkout) {
		this.checkout = checkout;
	}

	public String getGuests() {
		return this.guests;
	}

	public void setGuests(String guests) {
		this.guests = guests;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Listing getListing() {
		return this.listing;
	}

	public void setListing(Listing listing) {
		this.listing = listing;
	}

}