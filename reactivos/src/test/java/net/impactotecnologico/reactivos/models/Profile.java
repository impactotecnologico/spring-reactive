package net.impactotecnologico.reactivos.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
class Profile {

	@Id
	private String id;

	@SuppressWarnings("unused")
	private String email;
}