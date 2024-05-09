package com.example.project_search.data

import com.google.gson.annotations.SerializedName
import retrofit2.Response


data class KakaoImageData(
    @SerializedName("thumbnail_url")
    val thumbnailUrl: String,
    @SerializedName("display_sitename")
    val siteName: String,
    val datetime: String,
    var isliked: Boolean = false
)

data class ImageResponse(
    val documents: List<KakaoImageData>
)















//data class ImageModel(
//    val meta: Meta,
//    val documents: List<Document>
//)
//
//data class Meta(
//    @SerializedName("total_count")
//    val total_count: Int,
//    @SerializedName("pageable_count")
//    val pageable_count: Int,
//    @SerializedName("is_end")
//    val is_end: Boolean
//)
//
//data class Document(
//    @SerializedName("collection")
//    val collection: String,
//    @SerializedName("thumbnail_url")
//    val thumbnail_url: String?,
//    @SerializedName("image_url")
//    val image_url: String,
//    @SerializedName("width")
//    val width: Int,
//    @SerializedName("height")
//    val height: Int,
//    @SerializedName("display_sitename")
//    val display_sitename: String,
//    @SerializedName("doc_url")
//    val doc_url: String,
//    @SerializedName("datetime")
//    val datetime: String
//)
