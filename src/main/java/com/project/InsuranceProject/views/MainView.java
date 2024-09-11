//package com.project.InsuranceProject.views;
//
//import com.project.InsuranceProject.data.entity.Movie;
//import com.project.InsuranceProject.data.service.MovieService;
//import com.vaadin.flow.component.dependency.CssImport;
//import com.vaadin.flow.component.grid.Grid;
//import com.vaadin.flow.component.html.H3;
//import com.vaadin.flow.component.orderedlayout.VerticalLayout;
//import com.vaadin.flow.data.renderer.LitRenderer;
//import com.vaadin.flow.router.Route;
//import org.springframework.beans.factory.annotation.Autowired;
//
//// ApplicationServiceInitListener
//
// /**
// * A simple Vaadin View class that shows all Movies in a database.
// * <p>
// * See {@link MovieService} for details on the database, and
// * {@link } for adding more demo data.
// */
//@Route
//@CssImport("./styles/shared-styles.css")
//public class MainView extends VerticalLayout {
//
//    public MainView(@Autowired MovieService movieService) {
//        // Create and add header text
//        add(new H3("Accessing PostgreSQL database using JPA"));
//
//        // create Grid component
//        final Grid<Movie> movies = new Grid<>(Movie.class, false);
//
//        // fetch all movies from our Service
//        movies.setItems(movieService.getMovies());
//
//        // Configure columns
//        movies.addColumn(Movie::getTitle).setHeader("Title");
//        movies.addColumn(Movie::getReleaseYear).setHeader("Release Year").setWidth("120px");
//        movies.addColumn(movie -> movie.getDirector().getName()).setHeader("Director");
//
//        // Add link to IMDB column; the LitRenderer allows us to use an HTML link.
//        movies.addColumn(
//                LitRenderer.<Movie>of("<a href='${item.imdbLink}' target='_blank'>IMDB</a>")
//                        .withProperty("imdbLink", Movie::getImdbLink)
//        ).setHeader("IMDB Link");
//
//        // Add Grid to view
//        add(movies);
//    }
//}