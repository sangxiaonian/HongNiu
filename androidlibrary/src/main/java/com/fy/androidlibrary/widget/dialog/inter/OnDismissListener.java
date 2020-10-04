package com.fy.androidlibrary.widget.dialog.inter;

import androidx.fragment.app.DialogFragment;

/**
 * 作者：  on 2019/11/28.
 */
public interface OnDismissListener {
    /**
     * This method will be invoked when the dialog is dismissed.
     *
     * @param dialog the dialog that was dismissed will be passed into the
     *               method
     */
      void onDismiss(DialogFragment dialog);
}
