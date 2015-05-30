package co.edu.udea.carsharing.model.entities.enums;

public enum StateEnum {
	ACTIVE(0, "Activo"), CANCELED(1, "Cancelado"), FINALIZED(3, "Finalizado"), EXPIRED(
			4, "Vencido");

	private int state;
	private String description;

	private StateEnum(int state, String description) {
		this.state = state;
		this.description = description;
	}

	public static StateEnum findByState(int state) {
		for (StateEnum stateEnum : StateEnum.values()) {
			if (stateEnum.getState() == state) {

				return stateEnum;
			}
		}

		return null;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}