package com.retroarch.browser;

import android.app.Activity;
import com.retroarch.R;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.TextView;
import com.retroarch.browser.retroactivity.RetroActivityFuture;
import java.io.File;

public class LauncherActivity extends Activity {
  @Override
  protected void onCreate(Bundle b) {
    super.onCreate(b);
    setContentView(R.layout.activity_launcher);
    final TextView status = findViewById(R.id.txtStatus);
    Button play = findViewById(R.id.btnPlay);

    final File root = new File(Environment.getExternalStorageDirectory(), "LuckyStarLauncher");
    final File iso = new File(root, "game.iso");
    status.setText("Taruh ISO di: " + iso.getAbsolutePath());

    play.setOnClickListener(v -> {
      if (!iso.exists()) { status.setText("ISO belum ada di " + iso.getAbsolutePath()); return; }
      String core = getApplicationInfo().nativeLibraryDir + "/libppsspp_libretro.so";
      File cfg = new File(root, "retroarch.cfg");

      Intent i = new Intent(this, RetroActivityFuture.class);
      i.putExtra("ROM", iso.getAbsolutePath());
      i.putExtra("LIBRETRO", core);
      i.putExtra("CONFIGFILE", cfg.getAbsolutePath());
      i.putExtra("IME", "com.android.inputmethod.latin/.LatinIME");
      i.putExtra("DATADIR", getApplicationInfo().dataDir);
      i.putExtra("APK", getApplicationInfo().sourceDir);
      i.putExtra("SDCARD", Environment.getExternalStorageDirectory().getAbsolutePath());
      i.putExtra("QUITFOCUS", true);
      i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); startActivity(i); finish();
    });
  }
}
