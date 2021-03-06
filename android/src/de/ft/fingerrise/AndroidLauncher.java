package de.ft.fingerrise;

import android.os.Build;
import android.os.Bundle;

import android.view.View;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		View decorView = getWindow().getDecorView();


		config.useImmersiveMode = true;
		config.useWakelock = true;
		config.hideStatusBar = true;

		initialize(new FingerRise(), config);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
		}
	}
}
