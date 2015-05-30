package co.edu.udea.carsharing.ws.rest.contract;

public class RESTFulWebServicesContract {

	private RESTFulWebServicesContract() {
		super();
	}

	public static final class BrandtWebServicesContract {

		/*
		 * Parameteres
		 */

		/*
		 * Paths
		 */
		public static final String ROOT_PATH = "/brand";

		private BrandtWebServicesContract() {
			super();
		}
	}

	public static final class EventWebServicesContract {

		/*
		 * Parameteres
		 */
		public static final String EVENT_ID_PARAM = "eventId";

		/*
		 * Paths
		 */
		public static final String ROOT_PATH = "/event";
		public static final String FIND_ALL_PATH = "/all";
		public static final String INSERT_COMMENT = "/comment";
		public static final String JOIN_PARTNER = "/partner";

		private EventWebServicesContract() {
			super();
		}
	}

	public static final class UserWebServicesContract {

		/*
		 * Parameteres
		 */
		public static final String EMAIL_PARAM = "email";
		public static final String PASSWORD_PARAM = "password";

		/*
		 * Paths
		 */
		public static final String ROOT_PATH = "/user";
		public static final String FIND_USER_BY_EMAIL_PATH = "/email";
		public static final String FIND_CARS_BY_USER_PATH = "cars";

		private UserWebServicesContract() {
			super();
		}
	}
}