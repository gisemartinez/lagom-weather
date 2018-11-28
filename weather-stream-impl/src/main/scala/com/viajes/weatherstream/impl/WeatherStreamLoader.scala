package com.viajes.weatherstream.impl

import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.server._
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import play.api.libs.ws.ahc.AhcWSComponents
import com.viajes.weatherstream.api.WeatherStreamService
import com.viajes.weather.api.WeatherService
import com.softwaremill.macwire._

class WeatherStreamLoader extends LagomApplicationLoader {

  override def load(context: LagomApplicationContext): LagomApplication =
    new WeatherStreamApplication(context) {
      override def serviceLocator = NoServiceLocator
    }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new WeatherStreamApplication(context) with LagomDevModeComponents

  override def describeService = Some(readDescriptor[WeatherStreamService])
}

abstract class WeatherStreamApplication(context: LagomApplicationContext)
  extends LagomApplication(context)
    with AhcWSComponents {

  // Bind the service that this server provides
  override lazy val lagomServer = serverFor[WeatherStreamService](wire[WeatherStreamServiceImpl])

  // Bind the WeatherService client
  lazy val weatherService = serviceClient.implement[WeatherService]
}
