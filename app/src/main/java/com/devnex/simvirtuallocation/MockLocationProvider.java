package com.devnex.simvirtuallocation;


import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.location.provider.ProviderProperties;
import android.os.Build;
import android.os.SystemClock;

class MockLocationProvider {

    private final String providerName;
    private final Context ctx;

    MockLocationProvider(String name, Context ctx) {
        this.providerName = name;
        this.ctx = ctx;

        LocationManager lm = (LocationManager) ctx.getSystemService(
                Context.LOCATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            lm.addTestProvider(providerName, false, false, false, false, false,
                    true, true, ProviderProperties.POWER_USAGE_LOW, ProviderProperties.ACCURACY_FINE);
        } else {
            lm.addTestProvider(providerName, false, false, false, false, false,
                    true, true, 1, 1);
        }
        lm.setTestProviderEnabled(providerName, true);
    }

    void pushLocation(double lat, double lon,double alt,float accuracy) {
        LocationManager lm = (LocationManager) ctx.getSystemService(
                Context.LOCATION_SERVICE);

        Location mockLocation = new Location(providerName);
        mockLocation.setLatitude(lat);
        mockLocation.setLongitude(lon);
        mockLocation.setAltitude(alt);
        mockLocation.setTime(System.currentTimeMillis());
        mockLocation.setAccuracy(1);
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            mockLocation.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());
        }
        lm.setTestProviderLocation(providerName, mockLocation);
    }

    void shutdown() {
        LocationManager lm = (LocationManager) ctx.getSystemService(
                Context.LOCATION_SERVICE);
        lm.removeTestProvider(providerName);
    }
}
