package com.viajes.weatherstream.impl

import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.viajes.weatherstream.api.WeatherStreamService
import com.viajes.weather.api.WeatherService

import scala.concurrent.Future

/**
  * Implementation of the WeatherStreamService.
  */
class WeatherStreamServiceImpl(weatherService: WeatherService) extends WeatherStreamService {
  def stream = ServiceCall { hellos =>
    Future.successful(hellos.mapAsync(8)(weatherService.hello(_).invoke()))
  }
}
