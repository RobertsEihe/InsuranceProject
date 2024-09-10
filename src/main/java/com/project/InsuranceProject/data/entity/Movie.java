package com.project.InsuranceProject.data.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "movies")
public class Movie {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String title;

	@ManyToOne
	@JoinColumn(name = "director_id", nullable = false)
	private Director director;

	@Column(name = "release_year", nullable = false)
	private int releaseYear;

	@Column(name = "imdb_link")
	private String imdbLink;

	public Movie() {
	}

	public Movie(String title, int releaseYear, String imdbLink, Director director) {
		this.title = title;
		this.releaseYear = releaseYear;
		this.imdbLink = imdbLink;
		this.director = director;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Director getDirector() {
		return director;
	}

	public void setDirector(Director director) {
		this.director = director;
	}

	public int getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(int releaseYear) {
		this.releaseYear = releaseYear;
	}

	public String getImdbLink() {
		return imdbLink;
	}

	public void setImdbLink(String imdbLink) {
		this.imdbLink = imdbLink;
	}
// Getters and setters remain the same

	@Override
	public String toString() {
		return String.format("Movie[title= %s, releaseYear = %d]", title, releaseYear);
	}
}