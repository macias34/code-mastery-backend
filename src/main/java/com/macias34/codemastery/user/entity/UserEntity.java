package com.macias34.codemastery.user.entity;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.macias34.codemastery.course.entity.CategoryEntity;
import com.macias34.codemastery.course.entity.CourseEntity;
import com.macias34.codemastery.order.entity.OrderEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.criteria.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "user_")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String username;
	private String email;
	private Timestamp createdAt;

	@Enumerated(EnumType.STRING)
	private UserRole role;
	private String password;

	@OneToMany(mappedBy = "user")
	private List<OrderEntity> orders;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "personal_details_id",referencedColumnName = "id")
	private PersonalDetailsEntity personalDetails;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "invoice_details_id",referencedColumnName = "id")
	private InvoiceDetailsEntity invoiceDetails;

	@ManyToMany
	@JoinTable(name = "user_course", joinColumns = { @JoinColumn(name = "userId") }, inverseJoinColumns = {
			@JoinColumn(name = "courseId") })
	private Set<CourseEntity> courses = new HashSet<>();

	public UserEntity(String username, String email, String password, Timestamp createdAt, UserRole role) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.createdAt = createdAt;
		this.role = role;
	}

}
