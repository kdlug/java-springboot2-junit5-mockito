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
