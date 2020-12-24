package com.soumik.bdjobs.ui.adapters

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil


//
// Created by Soumik on 12/25/2020.
// Copyright (c) 2020 Soumik.  All rights reserved.
//
class DiffUtilItemCallback<T>: DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem==newItem
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem==newItem
    }
}