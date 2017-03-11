package me.wonwoo.support;

import javax.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Created by wonwoo on 2017. 3. 11..
 */
public class FakeHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return FakeParam.class.isAssignableFrom(parameter.getParameterType());
  }

  @Override
  public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
    HttpServletRequest httpServletRequest = (HttpServletRequest) nativeWebRequest.getNativeRequest();
    final String requestURI = httpServletRequest.getRequestURI();
    return new FakeParam(requestURI.replace("/api", ""),httpServletRequest.getMethod());
  }
}
