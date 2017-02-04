package com.shivamdev.contactsmanager.data;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import com.shivamdev.contactsmanager.common.TestDataFactory;
import com.shivamdev.contactsmanager.data.model.NamedResource;
import com.shivamdev.contactsmanager.data.model.Pokemon;
import com.shivamdev.contactsmanager.data.model.PokemonListResponse;
import com.shivamdev.contactsmanager.data.remote.MvpStarterService;
import com.shivamdev.contactsmanager.util.RxSchedulersOverrideRule;
import rx.Single;
import rx.observers.TestSubscriber;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DataManagerTest {

    @Rule
    // Must be added to every test class that targets app code that uses RxJava
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();
    @Mock
    MvpStarterService mMockMvpStarterService;
    private DataManager mDataManager;

    @Before
    public void setUp() {
        mDataManager = new DataManager(mMockMvpStarterService);
    }

    @Test
    public void getPokemonListCompletesAndEmitsPokemonList() {
        List<NamedResource> namedResourceList = TestDataFactory.makeNamedResourceList(5);
        PokemonListResponse pokemonListResponse =
                new PokemonListResponse();
        pokemonListResponse.results = namedResourceList;

        when(mMockMvpStarterService.getPokemonList(anyInt()))
                .thenReturn(Single.just(pokemonListResponse));

        TestSubscriber<List<String>> testSubscriber = new TestSubscriber<>();
        mDataManager.getPokemonList(10).subscribe(testSubscriber);
        testSubscriber.assertCompleted();
        testSubscriber.assertValue(TestDataFactory.makePokemonNameList(namedResourceList));
    }

    @Test
    public void getPokemonCompletesAndEmitsPokemon() {
        String name = "charmander";
        Pokemon pokemon = TestDataFactory.makePokemon(name);
        when(mMockMvpStarterService.getPokemon(anyString()))
                .thenReturn(Single.just(pokemon));

        TestSubscriber<Pokemon> testSubscriber = new TestSubscriber<>();
        mDataManager.getPokemon(name).subscribe(testSubscriber);
        testSubscriber.assertCompleted();
        testSubscriber.assertValue(pokemon);
    }

}
