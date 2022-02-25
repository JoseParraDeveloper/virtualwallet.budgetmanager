package com.virtualwallet.budgetmanager.entities;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.virtualwallet.budgetmanager.enumTypes.Gender;

@Entity
@Table(name = "people")
public class PersonEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(length = 20)
	@NotBlank
	@Size(min = 3, max = 20)
	@Pattern(regexp = "[A-Za-zÁÉÍÓÚáéíóúñÑ\s]*")
	private String name;
	@Column(length = 20)
	@NotBlank
	@Pattern(regexp = "[A-Za-zÁÉÍÓÚáéíóúñÑ\s]*")
	private String surname;
	@Column
	@Pattern(regexp = "^[0-9]*$")
	private String dni;
	@Column
	@Email
	private String email;
	@Column
	@Pattern(regexp = "^[0-9,+,\s]*$")
	private String phoneNumber;
	@Column(length = 30)
	private String direction;
	@Lob
	@Column(columnDefinition = "LONGBLOB")
	private String photo;
	@Temporal(TemporalType.DATE)
	private Date dateBirth;
	@Transient
	private int age;
	@Column
	private boolean enabled;
	@Enumerated(EnumType.STRING)
	private Gender gender;
	@Column
	@NotBlank
	private String username;
	@Column
	@NotBlank
	private String password;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "authorities_users", joinColumns = @JoinColumn(name = "person_id"), inverseJoinColumns = @JoinColumn(name = "authority_id"))
	private Set<AuthorityEntity> authority;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Date getDateBirth() {
		return dateBirth;
	}

	public void setDateBirth(Date dateBirth) {
		this.dateBirth = dateBirth;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<AuthorityEntity> getAuthority() {
		return authority;
	}

	public void setAuthority(Set<AuthorityEntity> authority) {
		this.authority = authority;
	}

}
