package app.view;

import app.model.Book;
import app.model.requestresponse.Request;
import app.model.requestresponse.Response;
import app.service.Service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class MainPresenterTest {

    private MainPresenter classUnderTest;
    private I_MainView viewMock;
    private Service titleServiceMock;
    private Service authorServiceMock;
    private Service topicServiceMock;

    @BeforeEach
    void setUp() {
        viewMock = mock(I_MainView.class);
        titleServiceMock = mock(Service.class);
        authorServiceMock = mock(Service.class);
        topicServiceMock = mock(Service.class);

        classUnderTest = new MainPresenter(viewMock, titleServiceMock, authorServiceMock, topicServiceMock);
    }


    @Test
    void titleSearchSuccess() throws IOException {
        String input = "ValidTitle";
        Book[] emptyBooks = new Book[0];
        Response mockResponse = new Response(emptyBooks);
        when(titleServiceMock.run(new Request(input))).thenReturn(mockResponse);

        classUnderTest.titleSearch(input);

        verify(viewMock, times(1)).displayTitleSearchResults(mockResponse.getResults());
        verify(viewMock, never()).displayErrorMessage(anyString());
    }

    @Test
    void titleSearchFailure() throws IOException {
        String input = "Exception";
        when(titleServiceMock.run(new Request(input))).thenThrow(IOException.class);

        classUnderTest.titleSearch(input);

        verify(viewMock, times(1)).displayErrorMessage(anyString());
    }

    @Test
    void authorSearchSuccess() throws IOException {
        String input = "ValidAuthor";
        Book[] emptyBooks = new Book[0];
        Response mockResponse = new Response(emptyBooks);
        when(authorServiceMock.run(new Request(input))).thenReturn(mockResponse);

        classUnderTest.authorSearch(input);

        verify(viewMock, times(1)).displayAuthorSearchResults(mockResponse.getResults());
        verify(viewMock, never()).displayErrorMessage(anyString());
    }

    @Test
    void authorSearchFailure() throws IOException {
        String input = "Exception";
        when(authorServiceMock.run(new Request(input))).thenThrow(IOException.class);

        classUnderTest.authorSearch(input);

        verify(viewMock, times(1)).displayErrorMessage(anyString());
    }

    @Test
    void topicSearchSuccess() throws IOException {
        String input = "ValidTopic";
        Book[] emptyBooks = new Book[0];
        Response mockResponse = new Response(emptyBooks);
        when(topicServiceMock.run(new Request(input))).thenReturn(mockResponse);

        classUnderTest.topicSearch(input);

        verify(viewMock, times(1)).displayTopicSearchResults(mockResponse.getResults());
        verify(viewMock, never()).displayErrorMessage(anyString());
    }

    @Test
    void topicSearchFailure() throws IOException {
        String input = "Exception";
        when(topicServiceMock.run(new Request(input))).thenThrow(IOException.class);

        classUnderTest.topicSearch(input);

        verify(viewMock, times(1)).displayErrorMessage(anyString());
    }

    @Test
    void inputFail() {
        classUnderTest.titleSearch("");
        classUnderTest.authorSearch("");
        classUnderTest.topicSearch("");

        // Assuming the error message for invalid input is specific to each method in the MainPresenter
        verify(viewMock, times(1)).displayErrorMessage("Invalid input.  Please enter a valid title.");
        verify(viewMock, times(1)).displayErrorMessage("Invalid input.  Please enter a valid author name.");
        verify(viewMock, times(1)).displayErrorMessage("Invalid input.  Please enter a valid topic.");
    }
}
