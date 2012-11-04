package com.kmrowiec.puzzlegame;

import java.io.FileNotFoundException;
import java.io.InputStream;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ViewSwitcher;

public class PuzzleActivity extends Activity {

	public static final int DIALOG_PAUSED_ID = 44;
	
	GameBoard board;
	int screenOrientation;
	Bitmap sourceImage;
	
	ViewSwitcher inGameViewSwitcher;
	
	private class PauseDialog extends Dialog implements android.view.View.OnClickListener{
		
		public PauseDialog(){
			super(PuzzleActivity.this, R.style.PauseMenuStyle);
			this.setContentView(R.layout.pause_menu);
			Button resumeButton = (Button) findViewById(R.id.pausemenu_resumeButton);
			resumeButton.setOnClickListener(this);
			Button quitButton = (Button) findViewById(R.id.pausemenu_quitButton);
			quitButton.setOnClickListener(this);
		}
		
		public void onClick(View v) {
			
			
			switch(v.getId()){
			case R.id.pausemenu_resumeButton:
				this.dismiss();
				break;
			case R.id.pausemenu_quitButton:
//				Intent intent = new Intent(this.getContext(), MainMenuActivity.class);
//				startActivity(intent);
				finish();
				break;
			}

		}
	}
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        screenOrientation = getIntent().getIntExtra(MainMenuActivity.EXTRA_BOARD_ORIENTATION, 1);
  
//        String str = screenOrientation == 0 ? "PORTRAIT" : "HORIZONTAL";
//    	  Log.d("KAMIL", "Orientation recorded by puzzleactivity: " + str);
        
        //locking the app in needed position
        if(screenOrientation == GameBoard.ORIENTATION_PORTRAIT)
        	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        else
        	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        
        //making the app full screen
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                               WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.activity_puzzle);
        
        //Setting backgrounds to black.
        ((RelativeLayout) findViewById(R.id.centerLayout)).setBackgroundColor(Color.BLACK);
        ((FrameLayout) findViewById(R.id.backgroundLayout)).setBackgroundColor(Color.BLACK);
        
        inGameViewSwitcher = (ViewSwitcher) findViewById(R.id.inGameViewSwitcher);
        
        //now the fun begins :>
        
        //Crating a game board.
        board = new GameBoard(decodeGameSizeFromIntent(),
        		(RelativeLayout) findViewById(R.id.centerLayout), 
        		screenOrientation, this);
        
        sourceImage = loadBitmapFromIntent();
        
        ImageView preview = (ImageView) findViewById(R.id.previewImageView);
        preview.setImageBitmap(sourceImage);
        
        PuzzleCreator creator = new PuzzleCreator(sourceImage, board);
        board.loadTiles(creator.createPuzzle());
        board.drawBoard();
        
        
             
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_puzzle, menu);
        return true;
    }

    private Dimension decodeGameSizeFromIntent(){
    	
    	Dimension size = null;
    	
    	String str = getIntent().getStringExtra(MainMenuActivity.EXTRA_GAMESIZE);
    	
    	String[] gameSizes = getResources().getStringArray(R.array.gamesizes);
    	
    	if(str.equals(gameSizes[0])) size = new Dimension(2,3);
    	else if(str.equals(gameSizes[1])) size = new Dimension(3,5);
    	else if(str.equals(gameSizes[2])) size = new Dimension(4,7);
    	else if(str.equals(gameSizes[3])) size = new Dimension(6,10);
    	else
    		throw new RuntimeException("Decoding game size from intent failed. String does not match.");
    	
    	return size;
    }
    
    private Bitmap loadBitmapFromIntent(){
    	
    	Bitmap selectedImage = null;
        Uri imgUri = (Uri) getIntent().getParcelableExtra(MainMenuActivity.EXTRA_IMGURI);
        
        try{
        	InputStream imageStream = getContentResolver().openInputStream(imgUri);
        	selectedImage = BitmapFactory.decodeStream(imageStream);
        }catch(FileNotFoundException ex){
        	Log.e("LOADING ERROR", "Cannot load picture from the URI given", ex);
        }
        
        /*
        if(selectedImage.getWidth()>selectedImage.getHeight()){
        	selectedImage = BitmapOperator.rotateBitmap(selectedImage, 90);
        }*/
        
        return selectedImage;
    }

	@Override
	protected void onRestart() {
		super.onRestart();
		//Intent intent = new Intent(this, MainMenuActivity.class);
		//startActivity(intent);
		showDialog(DIALOG_PAUSED_ID);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		
		PauseDialog dialog = new PauseDialog();
		
		if(id == DIALOG_PAUSED_ID){
			
	       // dialog.setContentView(R.layout.pause_menu);
	        
	        
		}
		return dialog;
	}

	@Override
	public void onBackPressed() {
		showDialog(DIALOG_PAUSED_ID);
	}
	
	public void inGameButtonsOnClick(View view){
		switch(view.getId()){
		
		case R.id.previewButton:
			inGameViewSwitcher.showNext();
			break;
			
		case R.id.backToGameButton:
			inGameViewSwitcher.showPrevious();
			break;
		}
	}
    
}
