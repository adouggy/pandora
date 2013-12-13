package me.promenade.pandora.view;

import me.promenade.pandora.R;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

public class PlayButton extends Button {

	private static final int[] STATE_PLAY = { R.attr.state_playing };
	private boolean isPlaying = false;
	
	public boolean isPlaying() {
		return isPlaying;
	}

	public void setPlaying(
			boolean isPlaying) {
		this.isPlaying = isPlaying;
	}

	public PlayButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected int[] onCreateDrawableState(
			int extraSpace) {
		final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
		if (isPlaying) {
			mergeDrawableStates(drawableState,
					STATE_PLAY);
		}
		return drawableState;
	}
}
