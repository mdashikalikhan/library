package com.ctos.dummy.library;

import com.ctos.dummy.library.entity.Aisle;
import com.ctos.dummy.library.entity.Book;
import com.ctos.dummy.library.entity.Library;
import com.ctos.dummy.library.repository.AisleRepository;
import com.ctos.dummy.library.repository.BookRepository;
import com.ctos.dummy.library.repository.LibraryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class LibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);
	}


	@Bean
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	}

	@Bean
	public CommandLineRunner loadSampleData(LibraryRepository libraryRepository,
									  AisleRepository aisleRepository,
									  BookRepository bookRepository){
		return args -> {
			// Books
			Book book1 = new Book();
			book1.setBookName("Biology 101");

			Book book2 = new Book();
			book2.setBookName("Physics Basics");

			Book book3 = new Book();
			book3.setBookName("Dinosaur Facts");

			Book book4 = new Book();
			book4.setBookName("World History");

			bookRepository.saveAll(List.of(book1, book2, book3, book4));

			Library centralLibrary = new Library();
			centralLibrary.setLibraryName("CENTRAL LIBRARY");

			Aisle naturalHistory = new Aisle();
			naturalHistory.setIsleName("NATURAL HISTORY");
			naturalHistory.setLibrary(centralLibrary);
			naturalHistory.getBooks().add(book1);
			naturalHistory.getBooks().add(book3);

			book1.getAisles().add(naturalHistory);
			book3.getAisles().add(naturalHistory);

			Aisle science = new Aisle();
			science.setIsleName("SCIENCE");
			science.setLibrary(centralLibrary);
			science.getBooks().add(book2);
			book2.getAisles().add(science);

			centralLibrary.getAisles().add(naturalHistory);
			centralLibrary.getAisles().add(science);

			libraryRepository.save(centralLibrary);

			Library communityLibrary = new Library();
			communityLibrary.setLibraryName("COMMUNITY LIBRARY");
			libraryRepository.save(communityLibrary);

			Library techLibrary = new Library();
			techLibrary.setLibraryName("TECH LIBRARY");

			Aisle programming = new Aisle();
			programming.setIsleName("PROGRAMMING");
			programming.setLibrary(techLibrary);
			programming.getBooks().add(book2);
			programming.getBooks().add(book4);

			book2.getAisles().add(programming);
			book4.getAisles().add(programming);

			techLibrary.getAisles().add(programming);
			libraryRepository.save(techLibrary);
		};
	}
}
