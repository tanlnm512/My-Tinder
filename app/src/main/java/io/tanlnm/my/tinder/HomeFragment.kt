package io.tanlnm.my.tinder

import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.TransitionAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import io.tanlnm.my.tinder.databinding.FragmentHomeBinding
import io.tanlnm.my.tinder.model.SwipeAction

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = _binding!!

    private lateinit var viewModel: HomeViewModel

    private var index = 0

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        bindActions()

        viewModel.swipeActionStream.observe(viewLifecycleOwner) {
            bindData(it)
        }
    }

    private fun bindActions() {
        binding.flNope.setOnClickListener {
            binding.motionLayout.progress = 0f
            binding.motionLayout.setTransition(R.id.rest, R.id.like)
            viewModel.swipe()
        }

        binding.flLove.setOnClickListener {
            binding.motionLayout.progress = 0f
            binding.motionLayout.setTransition(R.id.rest, R.id.like)
            viewModel.swipe()
        }

        binding.motionLayout.setTransitionListener(object : TransitionAdapter() {
            override fun onTransitionCompleted(motionLayout: MotionLayout, currentId: Int) {
                super.onTransitionCompleted(motionLayout, currentId)
                if (currentId == R.id.offScreenPass) {
                    binding.motionLayout.progress = 0f
                    binding.motionLayout.setTransition(R.id.rest, R.id.like)
                    viewModel.swipe()
                    binding.flNope.setBackgroundResource(R.drawable.view_circle_white)
                    binding.ivNope.setImageResource(R.drawable.ic_close)
                    binding.flLove.setBackgroundResource(R.drawable.view_circle_white)
                    binding.ivLove.setImageResource(R.drawable.ic_love)
                }

                if (currentId == R.id.offScreenLike) {
                    binding.motionLayout.progress = 0f
                    binding.motionLayout.setTransition(R.id.rest, R.id.like)
                    viewModel.swipe()
                    binding.flNope.setBackgroundResource(R.drawable.view_circle_white)
                    binding.ivNope.setImageResource(R.drawable.ic_close)
                    binding.flLove.setBackgroundResource(R.drawable.view_circle_white)
                    binding.ivLove.setImageResource(R.drawable.ic_love)
                }

                if (currentId == R.id.pass) {
                    binding.tvNope.visibility = View.VISIBLE
                    binding.tvLike.visibility = View.GONE
                    binding.flNope.setBackgroundResource(R.drawable.view_pass_bg)
                    binding.ivNope.setImageResource(R.drawable.ic_close)
                }

                if (currentId == R.id.like) {
                    binding.tvNope.visibility = View.GONE
                    binding.tvLike.visibility = View.VISIBLE
                    binding.flLove.setBackgroundResource(R.drawable.view_love_bg)
                    binding.ivLove.setImageResource(R.drawable.ic_love)
                }

                if (currentId == R.id.rest) {
                    binding.flNope.setBackgroundResource(R.drawable.view_circle_white)
                    binding.ivNope.setImageResource(R.drawable.ic_close)
                    binding.flLove.setBackgroundResource(R.drawable.view_circle_white)
                    binding.ivLove.setImageResource(R.drawable.ic_love)
                }
            }
        })
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun bindData(model: SwipeAction) {
        binding.ivMemberTop.setImageResource(model.top.images[0])
        binding.tvNameTop.text = model.top.name
        binding.tvAgeTop.text = model.top.age.toString()
        binding.tvAddressTop.text = "Live in ${model.top.address}"
        binding.tvDistanceTop.text = "Distance ${model.top.distance} km"

        binding.ivMemberBottom.setImageResource(model.bottom.images[0])
        binding.tvNameBottom.setText(model.bottom.name)
        binding.tvAgeBottom.text = model.bottom.age.toString()
        binding.tvAddressBottom.text = "Live in ${model.bottom.address}"
        binding.tvDistanceBottom.text = "Distance ${model.bottom.distance} km"

        index = 0

        binding.tvNope.visibility = View.GONE
        binding.tvLike.visibility = View.GONE

        if (model.top.images.isNotEmpty()) {
            binding.llNumImagesTop.removeAllViews()
            for (i in 0 until model.top.images.size) {
                val v = View(context)
                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f
                )
                params.setMargins(convertDpToPixel(requireContext(), 3.0f), 0, 0, 0)
                v.layoutParams = params
                v.layoutParams.height = 14
                v.setBackgroundResource(R.drawable.view_unselect)
                v.tag = "vTop$i"
                binding.llNumImagesTop.addView(v)

                if (i == index) {
                    v.setBackgroundResource(R.drawable.view_select)
                }
            }
            binding.llNumImagesTop.setWeightSum(model.top.images.size.toFloat())
        }

        if (model.bottom.images.isNotEmpty()) {
            binding.llNumImagesBottom.removeAllViews()
            for (i in 0 until model.bottom.images.size) {
                val v = View(context)
                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f
                )
                params.setMargins(convertDpToPixel(requireContext(), 5.0f), 0, 0, 0)
                v.layoutParams = params
                v.layoutParams.height = 14
                v.setBackgroundResource(R.drawable.view_unselect)
                v.tag = "vBottom$i"
                binding.llNumImagesBottom.addView(v)

                if (i == index) {
                    v.setBackgroundResource(R.drawable.view_select)
                }
            }
            binding.llNumImagesBottom.setWeightSum(model.bottom.images.size.toFloat())
        }

        val isLongClick = booleanArrayOf(false)
        binding.motionLayout.setOnLongClickListener {
            isLongClick[0] = true
            false
        }

        // Click event to change images
        binding.motionLayout.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                isLongClick[0] = false
            }
            if (event.action == MotionEvent.ACTION_UP) {
                binding.tvNope.visibility = View.GONE
                binding.tvLike.visibility = View.GONE
                val x = event.x.toInt()
                val y = event.y.toInt()
                if (x < 600 && y <= 1300 && y > 50 && !isLongClick[0]) {
                    // Left click
                    if (index > 0) {
                        index--
                        for (i in 0 until model.top.images.size) {
                            if (i == index) {
                                val view: View = binding.llNumImagesTop.findViewWithTag(
                                    "vTop$i"
                                )
                                view.setBackgroundResource(R.drawable.view_select)
                                binding.ivMemberTop.setImageResource(model.top.images.get(i))
                            } else {
                                val view: View = binding.llNumImagesTop.findViewWithTag(
                                    "vTop$i"
                                )
                                view.setBackgroundResource(R.drawable.view_unselect)
                            }
                        }
                    }
                }
                if (x > 600 && y <= 1300 && y > 50 && !isLongClick[0]) {
                    // Right click
                    if (index < model.top.images.size - 1) {
                        index++
                        for (i in 0 until model.top.images.size) {
                            if (i == index) {
                                val view: View = binding.llNumImagesTop.findViewWithTag(
                                    "vTop$i"
                                )
                                view.setBackgroundResource(R.drawable.view_select)
                                binding.ivMemberTop.setImageResource(model.top.images.get(i))
                            } else {
                                val view: View = binding.llNumImagesTop.findViewWithTag(
                                    "vTop$i"
                                )
                                view.setBackgroundResource(R.drawable.view_unselect)
                            }
                        }
                    }
                }
            }
            false
        }
    }

    private fun convertDpToPixel(context: Context, dpValue: Float): Int {
        val r = context.resources
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dpValue,
            r.displayMetrics
        ).toInt()
    }
}