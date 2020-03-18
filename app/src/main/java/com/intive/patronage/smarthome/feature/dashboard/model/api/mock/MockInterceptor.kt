package com.intive.patronage.smarthome.feature.dashboard.model.api.mock

import com.intive.patronage.smarthome.BuildConfig
import okhttp3.*

private const val SUCCESS_CODE = 200

class MockInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (BuildConfig.DEBUG) {
            val uri = chain.request().url().uri().toString()

            val responseString = when {
                uri.endsWith("dashboard") -> getListOfReposBeingStarredJson
                else -> ""
            }

            return chain.proceed(chain.request())
                .newBuilder()
                .code(SUCCESS_CODE)
                .protocol(Protocol.HTTP_2)
                .message(responseString)
                .body(
                    ResponseBody.create(
                        MediaType.parse("application/json"),

                        responseString.toByteArray()
                    )
                )
                .addHeader("content-type", "application/json")
                .build()

        } else {
            throw IllegalAccessError(
                "MockInterceptor is only meant for Testing Purposes and " +
                        "bound to be used only with DEBUG mode"
            )
        }

    }

}

const val getListOfReposBeingStarredJson = """
{
  "temperatureSensors": [
    {
      "id": 0,
      "type": "temperatureSensor",
      "value": 20
    }
  ],
  "windowSensors": [
    {
      "id": 0,
      "type": "windowSensor",
      "status": "open"
    }
  ],
  "windowBlinds": [
    {
      "id": 0,
      "type": "windowBlind",
      "position": 50
    }
  ],
  "RFIDSensors": [
    {
      "id": 0,
      "type": "RFIDSensor",
      "lastTag": {
        "id": 0,
        "type": "RFIDTag",
        "timestamp": 1584182283
      }
    }
  ],
  "smokeSensors": [
    {
      "id": 0,
      "type": "smokeSensor",
      "isSmokeDetected": true
    }
  ],
  "HVACStatus": {
    "type": "HVACStatus",
    "heating": true,
    "cooling": true
  },
  "HVACRooms": [
    {
      "id": 0,
      "type": "HVACRoom",
      "heating-temperature": 100,
      "cooling-temperature": 200,
      "hysteresis": 10,
      "temperatureSensorId": 0,
      "windowSensorIds": [
        0
      ]
    }
  ],
  "lights": [
    {
      "id": 0,
      "type": "RGBLight",
      "hue": 100,
      "saturation": 50,
      "value": 50
    },
    {
      "id": 1,
      "type": "RGBLight",
      "hue": 200,
      "saturation": 50,
      "value": 50
    },
    {
      "id": 2,
      "type": "RGBLight",
      "hue": 300,
      "saturation": 50,
      "value": 50
    }
  ]
}
"""