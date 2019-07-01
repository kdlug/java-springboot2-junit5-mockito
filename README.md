# JUnit 5 with Mockito

## What is Mockito ?

- Mocking framework for testing
- Mocks (Test Doubles) are alternate implentations of objects to replase real objects in tests
- Works well with dependency injections
- For the class under test, injected dependencies can be mocks

## Types of mocks

- *Dummy* - Objects used just to get the code to compile
- *Fake* - An object that has an implementation, but not production ready
- *Stub* - An objects with pre-defined answers to method calls
- *Mock* - An object with pre-defined answers to method calls and has expectations of executions. Can throw an exception if an unexpected invocation is detected
- *Spy* - In Mockito Spieces are Mock like wrappers around the actual objects

## Terminology

- Verify - Used to verify number of times a mocked method has been called
- Argument Matcher - Matches arguments passed to Mocked Method & will allow or disallow
- Argument Captor - Captures arguments passed to a Mocked Method. Allows you to perform assertions of what was passed it to method

## Annotations

@Mock - Creates a mock
@Spy - Creates a spy
@InjectMocks - Inject mocks / spy into a class under test
@Captor - Captures arguments to Mock

## Issues

Fix IllegalStateException: Could not initialize plugin MockMaker https://howtodoinjava.com/mockito/plugin-mockmaker-error/


## Set up mockito

There are 3 ways to setup mockito

### Inline mock

```java
public class InlineMockTest {
    @Test
    void testInlineMock() {
        Map mapMock = mock(Map.class);

        assertEquals(0, mapMock.size());
    }
}
```

### Annotation mock

```java
public class AnnotationMocksTest {
    @Mock
    Map<String, Object> mapMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testMock() {
        mapMock.put("keyvalue", "foo");
    }
}
```

### JUnit Extension

```java
@ExtendWith(MockitoExtension.class)
public class JunitExtensionTest {
    @Mock
    Map<String, Object> mapMock;

    @Test
    void testMock() {
        mapMock.put("key", "foo");
    }
}
```

## BDD Behavior Driven Development

- Unit tests are referred to as specification ie. specifications of behaviors.
- Test method names should be sentences ie. saveValidID.
- BDD tests are often written in given-when-then context
- Mockito has added BDD support in class BDDMockito
- Static method given allows you to configure mocks
- Static method then allows you to verify mock interactions

```java
// instead of
when(specialtyRepository.findById(1l)).thenReturn(Optional.of(speciality));
verify(specialtyRepository).findById(anyLong());
        // 
// BDD stype
given(specialtyRepository.findById(1l)).willReturn(Optional.of(speciality));
then(specialtyRepository).should().findById(anyLong());
```

Example 

```java
    @Test
    void findByIdBddTest() {
        // given
        Speciality speciality = new Speciality();
        given(specialtyRepository.findById(1l)).willReturn(Optional.of(speciality));

        // when
        Speciality foundSpecialty = service.findById(1l);

        // then
        Assertions.assertThat(foundSpecialty).isNotNull();
        then(specialtyRepository).should().findById(anyLong());
        then(specialtyRepository).shouldHaveNoMoreInteractions();
    }

```

## Throwing Exceptions

3 ways

```java
    @Test
    void testFoThrow() {
        // the most typical use of throwing exceptions
        doThrow(new RuntimeException("boom")).when(specialtyRepository).delete(any());

        assertThrows(RuntimeException.class, () -> specialtyRepository.delete(new Speciality()));

        verify(specialtyRepository).delete(any());
    }

    @Test
    void testFindByIDThrows() {
        // BDD approach
        given(specialtyRepository.findById(1l)).willThrow(new RuntimeException("boom"));

        assertThrows(RuntimeException.class, () -> service.findById(1l));

        then(specialtyRepository).should().findById(1l);
    }

    @Test
    void deleteBDD() {
        // BDD when a method returns void
        willThrow(new RuntimeException("boom")).given(specialtyRepository).delete(any());

        assertThrows(RuntimeException.class, () -> specialtyRepository.delete(new Speciality()));

        then(specialtyRepository).should().delete(any());
    }
    
```