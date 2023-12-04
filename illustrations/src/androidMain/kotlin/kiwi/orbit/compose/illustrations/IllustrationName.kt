package kiwi.orbit.compose.illustrations

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter

public enum class IllustrationName {
    Accommodation,
    AirHelp,
    AirportShuttle,
    AirportTransportTaxi,
    Ambulance,
    AppKiwi,
    AppQRCode,
    BaggageDrop,
    Boarding,
    BoardingPass,
    BusinessTravel,
    CabinBaggage,
    Cancelled,
    Chatbot,
    CompassCollectPoints,
    CompassDemoted,
    CompassEmailAdventurer,
    CompassEmailCaptain,
    CompassEmailPromoted,
    CompassEmailPromotedCaptain,
    CompassEmailScout,
    CompassPoints,
    CompassSaveOnBooking,
    CompassTravelPlan,
    Damage,
    DesktopSearch,
    EVisa,
    EnjoyApp,
    Error,
    Error404,
    FareLock,
    FareLockSuccess,
    FastBooking,
    FastTrack,
    FastTrackMan,
    Feedback,
    FlexibleDates,
    FlightDisruptions,
    GroundTransport404,
    Help,
    Improve,
    Insurance,
    InviteAFriend,
    Login,
    Lounge,
    Mailbox,
    Meal,
    MobileApp,
    MobileApp2,
    Money,
    MusicalInstruments,
    NetVerify,
    NoFavoriteFlights,
    NoFlightChange,
    NoNotification,
    NoResults,
    Nomad,
    NomadNeutral,
    Offline,
    OnlineCheckIn,
    OpenSearch,
    Parking,
    PassportUpdate,
    Pets,
    PlaceholderAirport,
    PlaceholderDestination,
    PlaceholderHotel,
    PlaceholderTours,
    PlaneAndMoney,
    PlaneDelayed,
    PriorityBoarding,
    Rating,
    ReferAFriend,
    RentalCar,
    Seating,
    SpecialAssistance,
    SportsEquipment,
    Success,
    Time,
    TimelineLeave,
    TimelinePick,
    Tours,
    Train,
    TransportTaxi,
    Wheelchair,
    WomanWithPhone,
}

@Composable
public fun IllustrationName.painter(): Painter = when (this) {
    IllustrationName.Accommodation -> Illustrations.Accommodation
    IllustrationName.AirHelp -> Illustrations.AirHelp
    IllustrationName.AirportShuttle -> Illustrations.AirportShuttle
    IllustrationName.AirportTransportTaxi -> Illustrations.AirportTransportTaxi
    IllustrationName.Ambulance -> Illustrations.Ambulance
    IllustrationName.AppKiwi -> Illustrations.AppKiwi
    IllustrationName.AppQRCode -> Illustrations.AppQRCode
    IllustrationName.BaggageDrop -> Illustrations.BaggageDrop
    IllustrationName.Boarding -> Illustrations.Boarding
    IllustrationName.BoardingPass -> Illustrations.BoardingPass
    IllustrationName.BusinessTravel -> Illustrations.BusinessTravel
    IllustrationName.CabinBaggage -> Illustrations.CabinBaggage
    IllustrationName.Cancelled -> Illustrations.Cancelled
    IllustrationName.Chatbot -> Illustrations.Chatbot
    IllustrationName.CompassCollectPoints -> Illustrations.CompassCollectPoints
    IllustrationName.CompassDemoted -> Illustrations.CompassDemoted
    IllustrationName.CompassEmailAdventurer -> Illustrations.CompassEmailAdventurer
    IllustrationName.CompassEmailCaptain -> Illustrations.CompassEmailCaptain
    IllustrationName.CompassEmailPromoted -> Illustrations.CompassEmailPromoted
    IllustrationName.CompassEmailPromotedCaptain -> Illustrations.CompassEmailPromotedCaptain
    IllustrationName.CompassEmailScout -> Illustrations.CompassEmailScout
    IllustrationName.CompassPoints -> Illustrations.CompassPoints
    IllustrationName.CompassSaveOnBooking -> Illustrations.CompassSaveOnBooking
    IllustrationName.CompassTravelPlan -> Illustrations.CompassTravelPlan
    IllustrationName.Damage -> Illustrations.Damage
    IllustrationName.DesktopSearch -> Illustrations.DesktopSearch
    IllustrationName.EVisa -> Illustrations.EVisa
    IllustrationName.EnjoyApp -> Illustrations.EnjoyApp
    IllustrationName.Error -> Illustrations.Error
    IllustrationName.Error404 -> Illustrations.Error404
    IllustrationName.FareLock -> Illustrations.FareLock
    IllustrationName.FareLockSuccess -> Illustrations.FareLockSuccess
    IllustrationName.FastBooking -> Illustrations.FastBooking
    IllustrationName.FastTrack -> Illustrations.FastTrack
    IllustrationName.FastTrackMan -> Illustrations.FastTrackMan
    IllustrationName.Feedback -> Illustrations.Feedback
    IllustrationName.FlexibleDates -> Illustrations.FlexibleDates
    IllustrationName.FlightDisruptions -> Illustrations.FlightDisruptions
    IllustrationName.GroundTransport404 -> Illustrations.GroundTransport404
    IllustrationName.Help -> Illustrations.Help
    IllustrationName.Improve -> Illustrations.Improve
    IllustrationName.Insurance -> Illustrations.Insurance
    IllustrationName.InviteAFriend -> Illustrations.InviteAFriend
    IllustrationName.Login -> Illustrations.Login
    IllustrationName.Lounge -> Illustrations.Lounge
    IllustrationName.Mailbox -> Illustrations.Mailbox
    IllustrationName.Meal -> Illustrations.Meal
    IllustrationName.MobileApp -> Illustrations.MobileApp
    IllustrationName.MobileApp2 -> Illustrations.MobileApp2
    IllustrationName.Money -> Illustrations.Money
    IllustrationName.MusicalInstruments -> Illustrations.MusicalInstruments
    IllustrationName.NetVerify -> Illustrations.NetVerify
    IllustrationName.NoFavoriteFlights -> Illustrations.NoFavoriteFlights
    IllustrationName.NoFlightChange -> Illustrations.NoFlightChange
    IllustrationName.NoNotification -> Illustrations.NoNotification
    IllustrationName.NoResults -> Illustrations.NoResults
    IllustrationName.Nomad -> Illustrations.Nomad
    IllustrationName.NomadNeutral -> Illustrations.NomadNeutral
    IllustrationName.Offline -> Illustrations.Offline
    IllustrationName.OnlineCheckIn -> Illustrations.OnlineCheckIn
    IllustrationName.OpenSearch -> Illustrations.OpenSearch
    IllustrationName.Parking -> Illustrations.Parking
    IllustrationName.PassportUpdate -> Illustrations.PassportUpdate
    IllustrationName.Pets -> Illustrations.Pets
    IllustrationName.PlaceholderAirport -> Illustrations.PlaceholderAirport
    IllustrationName.PlaceholderDestination -> Illustrations.PlaceholderDestination
    IllustrationName.PlaceholderHotel -> Illustrations.PlaceholderHotel
    IllustrationName.PlaceholderTours -> Illustrations.PlaceholderTours
    IllustrationName.PlaneAndMoney -> Illustrations.PlaneAndMoney
    IllustrationName.PlaneDelayed -> Illustrations.PlaneDelayed
    IllustrationName.PriorityBoarding -> Illustrations.PriorityBoarding
    IllustrationName.Rating -> Illustrations.Rating
    IllustrationName.ReferAFriend -> Illustrations.ReferAFriend
    IllustrationName.RentalCar -> Illustrations.RentalCar
    IllustrationName.Seating -> Illustrations.Seating
    IllustrationName.SpecialAssistance -> Illustrations.SpecialAssistance
    IllustrationName.SportsEquipment -> Illustrations.SportsEquipment
    IllustrationName.Success -> Illustrations.Success
    IllustrationName.Time -> Illustrations.Time
    IllustrationName.TimelineLeave -> Illustrations.TimelineLeave
    IllustrationName.TimelinePick -> Illustrations.TimelinePick
    IllustrationName.Tours -> Illustrations.Tours
    IllustrationName.Train -> Illustrations.Train
    IllustrationName.TransportTaxi -> Illustrations.TransportTaxi
    IllustrationName.Wheelchair -> Illustrations.Wheelchair
    IllustrationName.WomanWithPhone -> Illustrations.WomanWithPhone
}
