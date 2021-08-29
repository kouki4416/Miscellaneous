package com.example.rxretrofitpractice

data class BookData(
    var kind: String,
    var id: String,
    var etag: String,
    var selfLink: String,
    var volumeInfo: VolumeInfo,
    var saleInfo: SaleInfo,
    var accessInfo: AccessInfo,
    var searchInfo: SearchInfo
)

data class VolumeInfo(
    var title: String ,
    var subtitle: String,
    var authors: List<String>,
    var publisher: String,
    var industryIdentifiers: List<IndustryIdentifier>,
    var readingModes: ReadingMode,
    var pageCount: Int,
    var printType: String,
    var categories: List<String>,
    var averageRating: Int,
    var ratingsCount: Int,
    var maturityRating: String,
    var allowAnonLogging: Boolean,
    var contentVersion: String,
    var panelizationSummary: PanelizationSummary,
    var imageLinks: ImageLinks,
    var language: String,
    var previewLink: String,
    var infoLink: String,
    var canonicalVolumeLink: String
)

data class SaleInfo(
    var country: String,
    var saleability: String,
    var isEbook: Boolean
)

data class AccessInfo(
    var county: String,
    var viewability: String,
    var embeddable: Boolean,
    var publicDomain: Boolean,
    var textToSpeechPermission: String,
    var epub: Epub,
    var pdf: Pdf,
    var webReaderLink: String,
    var accessViewStatus: String,
    var quoteSharingAllowed: Boolean
)

data class SearchInfo(
    var textSnippet: String
)

data class IndustryIdentifier(
    var type: String,
    var identifier: String
)

data class ReadingMode(
    var text: Boolean,
    var image: Boolean
)

data class PanelizationSummary(
    var containsEpubBubbles: Boolean,
    var containsImageBubbles: Boolean
)

data class Epub(
    var isAvailable: Boolean
)

data class Pdf(
    var isAvailable: Boolean
)

data class ImageLinks(
    var smallThumbnail: String,
    var thumbnail: String
)