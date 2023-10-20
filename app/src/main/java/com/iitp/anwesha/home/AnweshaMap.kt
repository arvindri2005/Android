package com.iitp.anwesha.home

import android.animation.ValueAnimator
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import com.iitp.anwesha.R
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.iitp.anwesha.databinding.FragmentHomeBinding

class Map(
    val context: Context,
    val binding: FragmentHomeBinding

){


    @Composable
    fun AnweshaMap() {
        var scale  by remember {
            mutableStateOf(1f) }

        var offset  by remember {
            mutableStateOf(Offset.Zero) }

        BoxWithConstraints {
            val state = rememberTransformableState{zoomChange, panChange, rotationChange ->
                scale = (scale* zoomChange).coerceIn(1f,5f)
                val extraHeight = (scale-1)*constraints.maxHeight
                val extraWidth = (scale-1)*constraints.maxWidth

                val maxX=extraHeight/2
                val maxY=extraWidth/2


                offset = Offset(
                    x = (offset.x + scale*panChange.x).coerceIn(-maxX, + maxX),
                    y = (offset.y + scale*panChange.y).coerceIn(-maxY, + maxY)
                )

            }

            Box(modifier = Modifier
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    translationX = offset.x
                    translationY = offset.y
                }
                .transformable(state)){

                Image(painter = painterResource(id = R.drawable.map), contentDescription ="Anwesha Map")

                //Nescafe
                Pointer(id = R.drawable.nescafe,Modifier.padding(70.dp, 80.dp, 0.dp, 0.dp))
                //Sac
                Pointer(id = R.drawable.sac,Modifier.padding(115.dp, 78.dp, 0.dp, 0.dp))
                //gym
                Pointer(id = R.drawable.gym,Modifier.padding(0.dp, 0.dp, 0.dp, 0.dp))
                //basketball
                Pointer(id = R.drawable.basketball,Modifier.padding(0.dp, 0.dp, 0.dp, 0.dp))
                //nsit
                Pointer(id = R.drawable.nsit,Modifier.padding(0.dp, 0.dp, 0.dp, 0.dp))
                //food_court
                Pointer(id = R.drawable.food_court,Modifier.padding(0.dp, 0.dp, 0.dp, 0.dp))
                //senate
                Pointer(id = R.drawable.senate,Modifier.padding(0.dp, 0.dp, 0.dp, 0.dp))
                //helipad
                Pointer(id = R.drawable.helipad,Modifier.padding(0.dp, 0.dp, 0.dp, 0.dp))
                //main_stage
                Pointer(id = R.drawable.main_stage,Modifier.padding(0.dp, 0.dp, 0.dp, 0.dp))
                //lh1
                Pointer(id = R.drawable.lh1,Modifier.padding(0.dp, 0.dp, 0.dp, 0.dp))
                //lh2
                Pointer(id = R.drawable.lh2,Modifier.padding(0.dp, 0.dp, 0.dp, 0.dp))

            }

        }





    }
    @Composable
    fun Pointer(id: Int , modifier: Modifier = Modifier){
        Box(modifier = modifier){
            Image(painter = painterResource(id = id) , contentDescription =null,
                modifier= Modifier
                    .size(20.dp, 20.dp)
                    .clickable {

                    }
            )
        }
    }


}


