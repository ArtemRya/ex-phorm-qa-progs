jQuery(function(A){var B={init:function(C){A(".show-hidden-contributors",C).click(function(){A(".hidden-contributor",C).removeClass("hidden");
A(this).parent().remove();
return false
})
}};
A("div.plugin-contributors").each(function(){B.init(A(this))
})
});
