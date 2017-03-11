package me.wonwoo.support;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.core.MethodParameter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by wonwoo on 2017. 3. 11..
 */
public class FakeHandlerMethodArgumentResolverTests {

  private MethodParameter supportedMethodParameter;

  private FakeHandlerMethodArgumentResolver resolver = new FakeHandlerMethodArgumentResolver("/api");

  @Before
  public void setUp() throws Exception {
    this.supportedMethodParameter = new MethodParameter(Sample.class.getMethod("supportedMethod", FakeParam.class), 0);
  }


  @Test
  public void getAndUriTest() throws Exception {
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setMethod("GET");
    request.setRequestURI("/api/test");
    assertSupportedAndResult(supportedMethodParameter, new FakeParam("/test", "GET"), getWebRequest(request));
  }

  @Test
  public void postAndUriTest() throws Exception {
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setMethod("POST");
    request.setRequestURI("/api/test");
    assertSupportedAndResult(supportedMethodParameter, new FakeParam("/test", "POST"), getWebRequest(request));
  }

  @Test
  public void putAndUriTest() throws Exception {
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setMethod("PUT");
    request.setRequestURI("/api/test");
    assertSupportedAndResult(supportedMethodParameter, new FakeParam("/test", "PUT"), getWebRequest(request));
  }

  @Test
  public void patchAndUriTest() throws Exception {
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setMethod("PATCH");
    request.setRequestURI("/api/test");
    assertSupportedAndResult(supportedMethodParameter, new FakeParam("/test", "PATCH"), getWebRequest(request));
  }

  @Test
  public void deleteAndUriTest() throws Exception {
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setMethod("DELETE");
    request.setRequestURI("/api/test");
    assertSupportedAndResult(supportedMethodParameter, new FakeParam("/test", "DELETE"), getWebRequest(request));
  }

  protected void assertSupportedAndResult(MethodParameter parameter, FakeParam fakeParam, NativeWebRequest request) throws Exception {
    assertSupportedAndResult(parameter, fakeParam, request, resolver);
  }

  private void assertSupportedAndResult(MethodParameter parameter, FakeParam fakeParam, NativeWebRequest request,
                                        HandlerMethodArgumentResolver resolver) throws Exception {
    assertThat(resolver.supportsParameter(parameter), is(true));
    assertThat(resolver.resolveArgument(parameter, null, request, null), is(fakeParam));
  }

  private static NativeWebRequest getWebRequest(MockHttpServletRequest request) {
    return new ServletWebRequest(request);
  }

  private static NativeWebRequest getWebRequest() {
    return new ServletWebRequest(new MockHttpServletRequest());
  }


  interface Sample {

    void supportedMethod(FakeParam fakeParam);
  }
}