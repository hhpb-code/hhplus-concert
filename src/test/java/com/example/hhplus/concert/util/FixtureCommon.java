package com.example.hhplus.concert.util;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import com.navercorp.fixturemonkey.api.introspector.FailoverIntrospector;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import com.navercorp.fixturemonkey.api.plugin.SimpleValueJqwikPlugin;
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin;
import java.util.Arrays;

public class FixtureCommon {

  public static final FixtureMonkey SUT = FixtureMonkey.builder()
      .objectIntrospector(new FailoverIntrospector(
          Arrays.asList(
              FieldReflectionArbitraryIntrospector.INSTANCE,
              ConstructorPropertiesArbitraryIntrospector.INSTANCE
          )
      ))
      .plugin(new JakartaValidationPlugin())
      .plugin(new SimpleValueJqwikPlugin())
      .build();
}