package com.virtualwallet.budgetmanager.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.virtualwallet.budgetmanager.enumTypes.AuthorityEnum;

@Entity
@Table(name = "authorities")
public class Authority {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	@Enumerated(EnumType.STRING)
	private AuthorityEnum authority;

	@Column
	private String description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AuthorityEnum getAuthority() {
		return authority;
	}

	public void setAuthority(AuthorityEnum authority) {
		this.authority = authority;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
