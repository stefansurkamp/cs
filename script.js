function changeImageSize() {
			var scroll = getCurrentScroll();

			if(scroll >= 350) {
				$('#desktopHeader').css('height', 150);
				$('.titleContainer').css('bottom', 10);
				$('.codeSkillsIntro').css('opacity', 0);				
			}
			else {
				$('#desktopHeader').height(500-scroll);
				$('.titleContainer').css('bottom', (Math.floor(500-scroll)/2)-65);
				$('.codeSkillsIntro').css('opacity', (Math.floor(350-scroll))/110);
			}

		}

		function getCurrentScroll() {
			return window.pageYOffset || document.documentElement.scrollTop;
		}

		$( document ).ready(function() {
			if($('#desktopHeader').css('display') == 'block') {
				var scroll = getCurrentScroll();
				if(scroll >= 350) {
					$('#desktopHeader').css('height', 150);
					$('.titleContainer').css('bottom', 10);
					$('.codeSkillsIntro').css('opacity', 0);
				}
				else {
					$('#desktopHeader').height(500-scroll);
					$('.titleContainer').css('bottom', (Math.floor(500-scroll)/2)-65);
					$('.codeSkillsIntro').css('opacity', (Math.floor(350-scroll))/110);

				}
			}

			$('.codeSkillsTitle').on('click', function(e){
				$('body').animate({scrollTop:0}, '500');
				e.preventDefault();
			});

		});

		$(window).scroll(function() {
			if($('#desktopHeader').css('display') == 'block') {
				changeImageSize();
			}
		});