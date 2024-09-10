package com.project.InsuranceProject.data.service;

import com.project.InsuranceProject.data.entity.Movie;
import com.project.InsuranceProject.data.repositories.DirectorRepository;
import com.project.InsuranceProject.data.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Standard Spring Service class, where we access the database to get our data.
 */
@Service
public class MovieService {

	private final MovieRepository movieRepository;
	private final DirectorRepository directorRepository;

	@Autowired
	public MovieService(MovieRepository movieRepository, DirectorRepository directorRepository) {
		this.movieRepository = movieRepository;
		this.directorRepository = directorRepository;
	}

	/**
	 * @return All movies in the database, with director information.
	 */
	public List<Movie> getMovies() {
		return movieRepository.findAll();
	}

	// You can add more methods here as needed, for example:

	public Movie saveMovie(Movie movie) {
		return movieRepository.save(movie);
	}

	public void deleteMovie(Long id) {
		movieRepository.deleteById(id);
	}

	public Movie getMovieById(Long id) {
		return movieRepository.findById(id).orElse(null);
	}
}