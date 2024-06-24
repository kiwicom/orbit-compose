package kiwi.orbit.compose.icons

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter

public enum class IconName {
    Accommodation,
    AccountCircle,
    Admin,
    Ai,
    AirConditioning,
    Airplane,
    AirplaneDown,
    AirplaneLanding,
    AirplaneReturn,
    AirplaneTakeoff,
    AirplaneUp,
    AirplaneUpOff,
    AirportSecurity,
    Alert,
    AlertCircle,
    AlertOctagon,
    All,
    Android,
    Anywhere,
    AppNotification,
    Apple,
    ArrowDown,
    ArrowUp,
    Atm,
    Attachment,
    BaggageCabin,
    BaggageCabinLight,
    BaggageCabinNone,
    BaggageCabinNoneLight,
    BaggageChecked10,
    BaggageChecked10Light,
    BaggageChecked20,
    BaggageChecked20Light,
    BaggageChecked30,
    BaggageChecked30Light,
    BaggageCheckedNone,
    BaggageCheckedNoneLight,
    BaggagePersonal,
    BaggagePersonalLight,
    BaggagePersonalNone,
    BaggagePersonalNoneLight,
    BaggageRecheck,
    BaggageSet,
    BaggageSetLight,
    BaggageStorage,
    Bank,
    BillingDetails,
    BoardingGate,
    Boat,
    Bookmark,
    Bug,
    Bus,
    Cake,
    Calendar,
    CalendarAnytime,
    CalendarDuration,
    CalendarRange,
    CalendarTripLength,
    Camera,
    Car,
    CarDoor,
    CarRental,
    Chart,
    Chat,
    Check,
    CheckCircle,
    ChevronBackward,
    ChevronDoubleBackward,
    ChevronDoubleForward,
    ChevronDown,
    ChevronForward,
    ChevronUp,
    Child,
    ChildFriendly,
    Circle,
    CircleEmpty,
    CircleFilled,
    CircleSmall,
    CircleSmallEmpty,
    City,
    Clock,
    Close,
    CloseCircle,
    Cocktail,
    Code,
    CodeKiwi,
    Coffee,
    ColorPicker,
    Compare,
    Compass,
    ContactEmail,
    Copy,
    CreditCard,
    CustomerSupport,
    Dashboard,
    Deals,
    DealsV2,
    DeviceDesktop,
    DeviceMobile,
    Diamond,
    Document,
    Download,
    Duplicate,
    Edit,
    EditOff,
    Email,
    Entertainment,
    Exchange,
    Facebook,
    FamilyAll,
    FamilyHalf,
    Feedback,
    Filters,
    Flash,
    FlightDirect,
    FlightMulticity,
    FlightNomad,
    FlightReturn,
    FlightServices,
    Fuel,
    FullScreen,
    FullScreenOff,
    Gallery,
    GenderMan,
    GenderWoman,
    Github,
    GooglePlay,
    GpsFixed,
    GpsIos,
    GpsNotFixed,
    GpsOff,
    Grid,
    Gym,
    Heart,
    HeartOutline,
    History,
    Inbox,
    Infant,
    InformationCircle,
    Instagram,
    Insurance,
    InsuranceConfirmed,
    InsuranceOff,
    Invoice,
    ItemCompleted,
    Kiwicom,
    KiwicomCare,
    KiwicomGuarantee,
    KiwicomNoGuarantee,
    Leisure,
    Link,
    Linkedin,
    List,
    Location,
    LocationA,
    LocationAdd,
    LocationB,
    LocationC,
    LocationD,
    LocationE,
    LocationF,
    LocationG,
    LocationH,
    LocationI,
    LocationJ,
    Lock,
    LockOpen,
    Logout,
    Lounge,
    Map,
    Markdown,
    Meal,
    MenuHamburger,
    MenuKebab,
    MenuMeatballs,
    Messages,
    MessagesOutline,
    Minus,
    MinusCircle,
    Money,
    MoneyTransferIn,
    MoneyTransferOut,
    Moon,
    MusicalInstruments,
    NewWindow,
    NoFlash,
    NoRefund,
    NoRescheduling,
    Nonstop,
    Notification,
    NotificationAdd,
    NotificationOff,
    NotificationOn,
    OnlineCheckIn,
    OnlineCheckInOff,
    Outlook,
    Paid,
    Parking,
    Partners,
    Passenger,
    PassengerAdd,
    PassengerOutline,
    PassengerRemove,
    Passengers,
    Passport,
    Pet,
    Pharmacy,
    Phone,
    Pin,
    PinOutline,
    Placeholder,
    Play,
    Playground,
    Plus,
    PlusCircle,
    PlusMinus,
    Pool,
    PowerPlug,
    PowerPlugOff,
    PriceChange,
    PriorityBoarding,
    Profit,
    PromoCode,
    QrCode,
    QuestionCircle,
    Radar,
    RadiusSearch,
    RebookingLight,
    RebookingNoneLight,
    Refund,
    RefundLight,
    RefundNoneLight,
    Relax,
    Reload,
    Remove,
    Replace,
    Restaurant,
    RouteNoStops,
    RouteOneStop,
    RouteTwoStops,
    Search,
    Seat,
    SeatAisle,
    SeatExtraLegroom,
    SeatLight,
    SeatWindow,
    Security,
    SelfTransfer,
    Send,
    Settings,
    Share,
    ShareAndroid,
    ShareAndroidOutline,
    ShareIos,
    Shopping,
    ShowLess,
    ShowMore,
    Sightseeing,
    Sign,
    Smoking,
    SmokingOff,
    Sms,
    Sort,
    Spa,
    SportEquipment,
    Sports,
    Stackoverflow,
    StarEmpty,
    StarFull,
    Subway,
    Suitcase,
    Sun,
    Sunrise,
    Taxi,
    Terminal,
    TermsAndConditions,
    ThumbDown,
    ThumbUp,
    Ticket,
    TicketOutline,
    Tiktok,
    Timelapse,
    Timer,
    Tips,
    Toilets,
    Train,
    Transmission,
    Trip,
    Twitter,
    Uber,
    Upload,
    UserGroup,
    Visa,
    Visibility,
    VisibilityOff,
    Walk,
    Wallet,
    Wheelchair,
    Wifi,
    WifiOff,
    Youtube,
}

@Composable
public fun IconName.painter(): Painter = when (this) {
    IconName.Accommodation -> Icons.Accommodation
    IconName.AccountCircle -> Icons.AccountCircle
    IconName.Admin -> Icons.Admin
    IconName.Ai -> Icons.Ai
    IconName.AirConditioning -> Icons.AirConditioning
    IconName.Airplane -> Icons.Airplane
    IconName.AirplaneDown -> Icons.AirplaneDown
    IconName.AirplaneLanding -> Icons.AirplaneLanding
    IconName.AirplaneReturn -> Icons.AirplaneReturn
    IconName.AirplaneTakeoff -> Icons.AirplaneTakeoff
    IconName.AirplaneUp -> Icons.AirplaneUp
    IconName.AirplaneUpOff -> Icons.AirplaneUpOff
    IconName.AirportSecurity -> Icons.AirportSecurity
    IconName.Alert -> Icons.Alert
    IconName.AlertCircle -> Icons.AlertCircle
    IconName.AlertOctagon -> Icons.AlertOctagon
    IconName.All -> Icons.All
    IconName.Android -> Icons.Android
    IconName.Anywhere -> Icons.Anywhere
    IconName.AppNotification -> Icons.AppNotification
    IconName.Apple -> Icons.Apple
    IconName.ArrowDown -> Icons.ArrowDown
    IconName.ArrowUp -> Icons.ArrowUp
    IconName.Atm -> Icons.Atm
    IconName.Attachment -> Icons.Attachment
    IconName.BaggageCabin -> Icons.BaggageCabin
    IconName.BaggageCabinLight -> Icons.BaggageCabinLight
    IconName.BaggageCabinNone -> Icons.BaggageCabinNone
    IconName.BaggageCabinNoneLight -> Icons.BaggageCabinNoneLight
    IconName.BaggageChecked10 -> Icons.BaggageChecked10
    IconName.BaggageChecked10Light -> Icons.BaggageChecked10Light
    IconName.BaggageChecked20 -> Icons.BaggageChecked20
    IconName.BaggageChecked20Light -> Icons.BaggageChecked20Light
    IconName.BaggageChecked30 -> Icons.BaggageChecked30
    IconName.BaggageChecked30Light -> Icons.BaggageChecked30Light
    IconName.BaggageCheckedNone -> Icons.BaggageCheckedNone
    IconName.BaggageCheckedNoneLight -> Icons.BaggageCheckedNoneLight
    IconName.BaggagePersonal -> Icons.BaggagePersonal
    IconName.BaggagePersonalLight -> Icons.BaggagePersonalLight
    IconName.BaggagePersonalNone -> Icons.BaggagePersonalNone
    IconName.BaggagePersonalNoneLight -> Icons.BaggagePersonalNoneLight
    IconName.BaggageRecheck -> Icons.BaggageRecheck
    IconName.BaggageSet -> Icons.BaggageSet
    IconName.BaggageSetLight -> Icons.BaggageSetLight
    IconName.BaggageStorage -> Icons.BaggageStorage
    IconName.Bank -> Icons.Bank
    IconName.BillingDetails -> Icons.BillingDetails
    IconName.BoardingGate -> Icons.BoardingGate
    IconName.Boat -> Icons.Boat
    IconName.Bookmark -> Icons.Bookmark
    IconName.Bug -> Icons.Bug
    IconName.Bus -> Icons.Bus
    IconName.Cake -> Icons.Cake
    IconName.Calendar -> Icons.Calendar
    IconName.CalendarAnytime -> Icons.CalendarAnytime
    IconName.CalendarDuration -> Icons.CalendarDuration
    IconName.CalendarRange -> Icons.CalendarRange
    IconName.CalendarTripLength -> Icons.CalendarTripLength
    IconName.Camera -> Icons.Camera
    IconName.Car -> Icons.Car
    IconName.CarDoor -> Icons.CarDoor
    IconName.CarRental -> Icons.CarRental
    IconName.Chart -> Icons.Chart
    IconName.Chat -> Icons.Chat
    IconName.Check -> Icons.Check
    IconName.CheckCircle -> Icons.CheckCircle
    IconName.ChevronBackward -> Icons.ChevronBackward
    IconName.ChevronDoubleBackward -> Icons.ChevronDoubleBackward
    IconName.ChevronDoubleForward -> Icons.ChevronDoubleForward
    IconName.ChevronDown -> Icons.ChevronDown
    IconName.ChevronForward -> Icons.ChevronForward
    IconName.ChevronUp -> Icons.ChevronUp
    IconName.Child -> Icons.Child
    IconName.ChildFriendly -> Icons.ChildFriendly
    IconName.Circle -> Icons.Circle
    IconName.CircleEmpty -> Icons.CircleEmpty
    IconName.CircleFilled -> Icons.CircleFilled
    IconName.CircleSmall -> Icons.CircleSmall
    IconName.CircleSmallEmpty -> Icons.CircleSmallEmpty
    IconName.City -> Icons.City
    IconName.Clock -> Icons.Clock
    IconName.Close -> Icons.Close
    IconName.CloseCircle -> Icons.CloseCircle
    IconName.Cocktail -> Icons.Cocktail
    IconName.Code -> Icons.Code
    IconName.CodeKiwi -> Icons.CodeKiwi
    IconName.Coffee -> Icons.Coffee
    IconName.ColorPicker -> Icons.ColorPicker
    IconName.Compare -> Icons.Compare
    IconName.Compass -> Icons.Compass
    IconName.ContactEmail -> Icons.ContactEmail
    IconName.Copy -> Icons.Copy
    IconName.CreditCard -> Icons.CreditCard
    IconName.CustomerSupport -> Icons.CustomerSupport
    IconName.Dashboard -> Icons.Dashboard
    IconName.Deals -> Icons.Deals
    IconName.DealsV2 -> Icons.DealsV2
    IconName.DeviceDesktop -> Icons.DeviceDesktop
    IconName.DeviceMobile -> Icons.DeviceMobile
    IconName.Diamond -> Icons.Diamond
    IconName.Document -> Icons.Document
    IconName.Download -> Icons.Download
    IconName.Duplicate -> Icons.Duplicate
    IconName.Edit -> Icons.Edit
    IconName.EditOff -> Icons.EditOff
    IconName.Email -> Icons.Email
    IconName.Entertainment -> Icons.Entertainment
    IconName.Exchange -> Icons.Exchange
    IconName.Facebook -> Icons.Facebook
    IconName.FamilyAll -> Icons.FamilyAll
    IconName.FamilyHalf -> Icons.FamilyHalf
    IconName.Feedback -> Icons.Feedback
    IconName.Filters -> Icons.Filters
    IconName.Flash -> Icons.Flash
    IconName.FlightDirect -> Icons.FlightDirect
    IconName.FlightMulticity -> Icons.FlightMulticity
    IconName.FlightNomad -> Icons.FlightNomad
    IconName.FlightReturn -> Icons.FlightReturn
    IconName.FlightServices -> Icons.FlightServices
    IconName.Fuel -> Icons.Fuel
    IconName.FullScreen -> Icons.FullScreen
    IconName.FullScreenOff -> Icons.FullScreenOff
    IconName.Gallery -> Icons.Gallery
    IconName.GenderMan -> Icons.GenderMan
    IconName.GenderWoman -> Icons.GenderWoman
    IconName.Github -> Icons.Github
    IconName.GooglePlay -> Icons.GooglePlay
    IconName.GpsFixed -> Icons.GpsFixed
    IconName.GpsIos -> Icons.GpsIos
    IconName.GpsNotFixed -> Icons.GpsNotFixed
    IconName.GpsOff -> Icons.GpsOff
    IconName.Grid -> Icons.Grid
    IconName.Gym -> Icons.Gym
    IconName.Heart -> Icons.Heart
    IconName.HeartOutline -> Icons.HeartOutline
    IconName.History -> Icons.History
    IconName.Inbox -> Icons.Inbox
    IconName.Infant -> Icons.Infant
    IconName.InformationCircle -> Icons.InformationCircle
    IconName.Instagram -> Icons.Instagram
    IconName.Insurance -> Icons.Insurance
    IconName.InsuranceConfirmed -> Icons.InsuranceConfirmed
    IconName.InsuranceOff -> Icons.InsuranceOff
    IconName.Invoice -> Icons.Invoice
    IconName.ItemCompleted -> Icons.ItemCompleted
    IconName.Kiwicom -> Icons.Kiwicom
    IconName.KiwicomCare -> Icons.KiwicomCare
    IconName.KiwicomGuarantee -> Icons.KiwicomGuarantee
    IconName.KiwicomNoGuarantee -> Icons.KiwicomNoGuarantee
    IconName.Leisure -> Icons.Leisure
    IconName.Link -> Icons.Link
    IconName.Linkedin -> Icons.Linkedin
    IconName.List -> Icons.List
    IconName.Location -> Icons.Location
    IconName.LocationA -> Icons.LocationA
    IconName.LocationAdd -> Icons.LocationAdd
    IconName.LocationB -> Icons.LocationB
    IconName.LocationC -> Icons.LocationC
    IconName.LocationD -> Icons.LocationD
    IconName.LocationE -> Icons.LocationE
    IconName.LocationF -> Icons.LocationF
    IconName.LocationG -> Icons.LocationG
    IconName.LocationH -> Icons.LocationH
    IconName.LocationI -> Icons.LocationI
    IconName.LocationJ -> Icons.LocationJ
    IconName.Lock -> Icons.Lock
    IconName.LockOpen -> Icons.LockOpen
    IconName.Logout -> Icons.Logout
    IconName.Lounge -> Icons.Lounge
    IconName.Map -> Icons.Map
    IconName.Markdown -> Icons.Markdown
    IconName.Meal -> Icons.Meal
    IconName.MenuHamburger -> Icons.MenuHamburger
    IconName.MenuKebab -> Icons.MenuKebab
    IconName.MenuMeatballs -> Icons.MenuMeatballs
    IconName.Messages -> Icons.Messages
    IconName.MessagesOutline -> Icons.MessagesOutline
    IconName.Minus -> Icons.Minus
    IconName.MinusCircle -> Icons.MinusCircle
    IconName.Money -> Icons.Money
    IconName.MoneyTransferIn -> Icons.MoneyTransferIn
    IconName.MoneyTransferOut -> Icons.MoneyTransferOut
    IconName.Moon -> Icons.Moon
    IconName.MusicalInstruments -> Icons.MusicalInstruments
    IconName.NewWindow -> Icons.NewWindow
    IconName.NoFlash -> Icons.NoFlash
    IconName.NoRefund -> Icons.NoRefund
    IconName.NoRescheduling -> Icons.NoRescheduling
    IconName.Nonstop -> Icons.Nonstop
    IconName.Notification -> Icons.Notification
    IconName.NotificationAdd -> Icons.NotificationAdd
    IconName.NotificationOff -> Icons.NotificationOff
    IconName.NotificationOn -> Icons.NotificationOn
    IconName.OnlineCheckIn -> Icons.OnlineCheckIn
    IconName.OnlineCheckInOff -> Icons.OnlineCheckInOff
    IconName.Outlook -> Icons.Outlook
    IconName.Paid -> Icons.Paid
    IconName.Parking -> Icons.Parking
    IconName.Partners -> Icons.Partners
    IconName.Passenger -> Icons.Passenger
    IconName.PassengerAdd -> Icons.PassengerAdd
    IconName.PassengerOutline -> Icons.PassengerOutline
    IconName.PassengerRemove -> Icons.PassengerRemove
    IconName.Passengers -> Icons.Passengers
    IconName.Passport -> Icons.Passport
    IconName.Pet -> Icons.Pet
    IconName.Pharmacy -> Icons.Pharmacy
    IconName.Phone -> Icons.Phone
    IconName.Pin -> Icons.Pin
    IconName.PinOutline -> Icons.PinOutline
    IconName.Placeholder -> Icons.Placeholder
    IconName.Play -> Icons.Play
    IconName.Playground -> Icons.Playground
    IconName.Plus -> Icons.Plus
    IconName.PlusCircle -> Icons.PlusCircle
    IconName.PlusMinus -> Icons.PlusMinus
    IconName.Pool -> Icons.Pool
    IconName.PowerPlug -> Icons.PowerPlug
    IconName.PowerPlugOff -> Icons.PowerPlugOff
    IconName.PriceChange -> Icons.PriceChange
    IconName.PriorityBoarding -> Icons.PriorityBoarding
    IconName.Profit -> Icons.Profit
    IconName.PromoCode -> Icons.PromoCode
    IconName.QrCode -> Icons.QrCode
    IconName.QuestionCircle -> Icons.QuestionCircle
    IconName.Radar -> Icons.Radar
    IconName.RadiusSearch -> Icons.RadiusSearch
    IconName.RebookingLight -> Icons.RebookingLight
    IconName.RebookingNoneLight -> Icons.RebookingNoneLight
    IconName.Refund -> Icons.Refund
    IconName.RefundLight -> Icons.RefundLight
    IconName.RefundNoneLight -> Icons.RefundNoneLight
    IconName.Relax -> Icons.Relax
    IconName.Reload -> Icons.Reload
    IconName.Remove -> Icons.Remove
    IconName.Replace -> Icons.Replace
    IconName.Restaurant -> Icons.Restaurant
    IconName.RouteNoStops -> Icons.RouteNoStops
    IconName.RouteOneStop -> Icons.RouteOneStop
    IconName.RouteTwoStops -> Icons.RouteTwoStops
    IconName.Search -> Icons.Search
    IconName.Seat -> Icons.Seat
    IconName.SeatAisle -> Icons.SeatAisle
    IconName.SeatExtraLegroom -> Icons.SeatExtraLegroom
    IconName.SeatLight -> Icons.SeatLight
    IconName.SeatWindow -> Icons.SeatWindow
    IconName.Security -> Icons.Security
    IconName.SelfTransfer -> Icons.SelfTransfer
    IconName.Send -> Icons.Send
    IconName.Settings -> Icons.Settings
    IconName.Share -> Icons.Share
    IconName.ShareAndroid -> Icons.ShareAndroid
    IconName.ShareAndroidOutline -> Icons.ShareAndroidOutline
    IconName.ShareIos -> Icons.ShareIos
    IconName.Shopping -> Icons.Shopping
    IconName.ShowLess -> Icons.ShowLess
    IconName.ShowMore -> Icons.ShowMore
    IconName.Sightseeing -> Icons.Sightseeing
    IconName.Sign -> Icons.Sign
    IconName.Smoking -> Icons.Smoking
    IconName.SmokingOff -> Icons.SmokingOff
    IconName.Sms -> Icons.Sms
    IconName.Sort -> Icons.Sort
    IconName.Spa -> Icons.Spa
    IconName.SportEquipment -> Icons.SportEquipment
    IconName.Sports -> Icons.Sports
    IconName.Stackoverflow -> Icons.Stackoverflow
    IconName.StarEmpty -> Icons.StarEmpty
    IconName.StarFull -> Icons.StarFull
    IconName.Subway -> Icons.Subway
    IconName.Suitcase -> Icons.Suitcase
    IconName.Sun -> Icons.Sun
    IconName.Sunrise -> Icons.Sunrise
    IconName.Taxi -> Icons.Taxi
    IconName.Terminal -> Icons.Terminal
    IconName.TermsAndConditions -> Icons.TermsAndConditions
    IconName.ThumbDown -> Icons.ThumbDown
    IconName.ThumbUp -> Icons.ThumbUp
    IconName.Ticket -> Icons.Ticket
    IconName.TicketOutline -> Icons.TicketOutline
    IconName.Tiktok -> Icons.Tiktok
    IconName.Timelapse -> Icons.Timelapse
    IconName.Timer -> Icons.Timer
    IconName.Tips -> Icons.Tips
    IconName.Toilets -> Icons.Toilets
    IconName.Train -> Icons.Train
    IconName.Transmission -> Icons.Transmission
    IconName.Trip -> Icons.Trip
    IconName.Twitter -> Icons.Twitter
    IconName.Uber -> Icons.Uber
    IconName.Upload -> Icons.Upload
    IconName.UserGroup -> Icons.UserGroup
    IconName.Visa -> Icons.Visa
    IconName.Visibility -> Icons.Visibility
    IconName.VisibilityOff -> Icons.VisibilityOff
    IconName.Walk -> Icons.Walk
    IconName.Wallet -> Icons.Wallet
    IconName.Wheelchair -> Icons.Wheelchair
    IconName.Wifi -> Icons.Wifi
    IconName.WifiOff -> Icons.WifiOff
    IconName.Youtube -> Icons.Youtube
}
