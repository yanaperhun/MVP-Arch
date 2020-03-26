package com.qati.cats;

import com.qati.cats.data.models.Cat;
import com.qati.cats.data.repository.ICatRepository;
import com.qati.cats.domain.interactor.GetAllCatsInteractor;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import retrofit2.HttpException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AllCatsPresenterTest {

    @Mock
    private  ICatRepository repository;
    @InjectMocks
    private GetAllCatsInteractor getAllCatsInteractor;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getCats_success(){
        when(repository.getAllCats()).thenReturn(Single.just(new ArrayList<>()));

        TestObserver<List<Cat>> testObserver = TestObserver.create();

        getAllCatsInteractor.getCats().subscribe(testObserver);

        testObserver.awaitTerminalEvent();
        testObserver.assertComplete();
        testObserver.assertNoErrors();

        verify(repository).getAllCats();
    }

    @Test
    public void getCats_error(){
        when(repository.getAllCats()).thenReturn(Single.error(mock(HttpException.class)));

        TestObserver<List<Cat>> testObserver = TestObserver.create();

        getAllCatsInteractor.getCats().subscribe(testObserver);

        testObserver.awaitTerminalEvent();
        testObserver.assertNotComplete();
        testObserver.assertError(HttpException.class);

        verify(repository).getAllCats();
    }
}
