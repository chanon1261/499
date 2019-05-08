package com.independent.m.mproject499.model


import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import nz.bradcampbell.paperparcel.PaperParcel

@RealmClass
@PaperParcel
open class Answers : RealmObject() {
    @PrimaryKey
    var id: Int? = null
    var intent: String? = null
    var condition: String? = null
    var answer: String? = null
    var on_answer: String? = null
}

@RealmClass
@PaperParcel
open class TestWord : RealmObject() {
    @PrimaryKey
    var id: Int? = null
    var word: String? = null
    var meaning: String? = null
    var desc_th: String? = null
    var desc_eng: String? = null
}

@RealmClass
@PaperParcel
open class WordHistory : RealmObject() {
    @PrimaryKey
    var id: String? = null
    var correct: Int = 0
    var fail: Int = 0
}