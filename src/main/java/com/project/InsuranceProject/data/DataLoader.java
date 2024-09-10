//package com.project.InsuranceProject.data;
//
//import com.project.InsuranceProject.data.entity.Director;
//import com.project.InsuranceProject.data.entity.Movie;
//import com.project.InsuranceProject.data.repositories.DirectorRepository;
//import com.project.InsuranceProject.data.repositories.MovieRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//public class DataLoader implements CommandLineRunner {
//
//    private final DirectorRepository directorRepository;
//    private final MovieRepository movieRepository;
//
//    public DataLoader(DirectorRepository directorRepository, MovieRepository movieRepository) {
//        this.directorRepository = directorRepository;
//        this.movieRepository = movieRepository;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        // Check if data already exists
//        if (directorRepository.count() == 0) {
//            loadData();
//        }
//    }
//
//    private void loadData() {
//        Director director1 = directorRepository.save(new Director("F. Gary Gray"));
//        Director director2 = directorRepository.save(new Director("Rian Johnson"));
//
//        movieRepository.save(new Movie("TestLaw Abiding Citizen", 2009, "https://www.imdb.com/title/tt1197624/", director1));
//        movieRepository.save(new Movie("Knives Out", 2019, "https://www.imdb.com/title/tt8946378/", director2));
//        movieRepository.save(new Movie("The Last Jedi", 2017, "https://www.imdb.com/title/tt2527336/", director2));
//    }
//}