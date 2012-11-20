$(function () {
    $(".likeText:contains('0')").hide();

    $(".like").click(function() {
        var pin = $(this).parent();
        var pinId = parseInt(pin.children(".pinID").text());
        $.post("/board/likePin/"+pinId, function(data) {
            pin.children(".likeText").text(data+" people like this.");
            if (pin.children(".like").text() == "Like")
                pin.children(".like").text("Unlike");
            else
                pin.children(".like").text("Like");
            if (data == 0)
                pin.children(".likeText").fadeOut();
            else
                pin.children(".likeText").fadeIn();
        }).error(function() {
            pin.children(".likeText").text("Error: Unable to like");
            pin.children(".likeText").fadeIn();
        })

    });
});