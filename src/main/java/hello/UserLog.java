package hello;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Table(name = "user_log")
public class UserLog {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String userId;

	@NotNull
	@Enumerated(EnumType.STRING)
	private ActionType actionType;

	@Column(name = "created_at", nullable = false)
	@CreatedDate
	@Convert(converter = DefaultLocalDateTimeConverter.class)
	private LocalDateTime createdAt =  LocalDateTime.now();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public ActionType getActionType() {
		return actionType;
	}

	public void setActionType(ActionType actionType) {
		this.actionType = actionType;
	}

	@JsonIgnore
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Long getLogDate() {
		return createdAt.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000;
	}


}
