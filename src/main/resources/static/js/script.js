(function () {
  'use strict';

  var burger = document.querySelector('.header__burger');
  var nav = document.querySelector('.nav');
  var backdrop = document.querySelector('.nav__backdrop');
  var navClose = document.querySelector('.nav__close');

  function openNav() {
    nav.classList.add('nav--open');
    burger.classList.add('header__burger--open');
    burger.setAttribute('aria-expanded', 'true');
    burger.setAttribute('aria-label', 'Закрыть меню');
    backdrop.classList.add('nav__backdrop--visible');
    document.body.style.overflow = 'hidden';
    setTimeout(function () { if (navClose) navClose.focus(); }, 350);
  }

  function closeNav() {
    nav.classList.remove('nav--open');
    burger.classList.remove('header__burger--open');
    burger.setAttribute('aria-expanded', 'false');
    burger.setAttribute('aria-label', 'Открыть меню');
    backdrop.classList.remove('nav__backdrop--visible');
    document.body.style.overflow = '';
    burger.focus();
  }

  if (burger && nav) {
    burger.addEventListener('click', function () {
      nav.classList.contains('nav--open') ? closeNav() : openNav();
    });

    if (navClose) {
      navClose.addEventListener('click', closeNav);
    }

    if (backdrop) {
      backdrop.addEventListener('click', closeNav);
    }

    document.addEventListener('keydown', function (e) {
      if (e.key === 'Escape' && nav.classList.contains('nav--open')) {
        closeNav();
      }
    });

    nav.querySelectorAll('.nav__link, .nav__cta').forEach(function (link) {
      link.addEventListener('click', closeNav);
    });
  }

  var tabButtons = document.querySelectorAll('.prices-tab');
  var panels = document.querySelectorAll('.prices-panel');
  var pricesPanels = document.querySelector('.prices-panels');
  var pricesWrap = document.querySelector('.prices-tabs-and-panels');
  var mobileQuery = window.matchMedia('(max-width: 480px)');

  function isMobile() {
    return mobileQuery.matches;
  }

  function restorePanelsToPanels() {
    if (!pricesPanels) return;
    panels.forEach(function (p) {
      if (p.parentNode !== pricesPanels) {
        pricesPanels.appendChild(p);
      }
    });
  }

  function movePanelAfterTab(btn, panel) {
    btn.insertAdjacentElement('afterend', panel);
  }

  tabButtons.forEach(function (btn) {
    btn.addEventListener('click', function () {
      var tabId = this.getAttribute('data-tab');
      var self = this;

      tabButtons.forEach(function (b) { b.classList.remove('prices-tab--active'); });
      panels.forEach(function (p) { p.classList.remove('prices-panel--active'); });

      self.classList.add('prices-tab--active');
      var panel = document.getElementById('panel-' + tabId);
      if (panel) {
        panel.classList.add('prices-panel--active');
        if (isMobile()) {
          movePanelAfterTab(self, panel);
        }
      }
    });
  });

  function initMobilePrices() {
    if (!isMobile()) return;
    var activeBtn = document.querySelector('.prices-tab--active');
    var activePanel = document.querySelector('.prices-panel--active');
    if (activeBtn && activePanel) {
      movePanelAfterTab(activeBtn, activePanel);
    }
  }

  function onBreakpointChange(mq) {
    if (mq.matches) {
      initMobilePrices();
    } else {
      restorePanelsToPanels();
    }
  }

  if (mobileQuery.addEventListener) {
    mobileQuery.addEventListener('change', onBreakpointChange);
  } else {
    mobileQuery.addListener(onBreakpointChange);
  }

  if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', initMobilePrices);
  } else {
    initMobilePrices();
  }

  document.querySelectorAll('[data-advantage-toggle]').forEach(function (head) {
    head.addEventListener('click', function () {
      var card = this.closest('.advantage-card');
      var isOpen = card.classList.contains('advantage-card--open');
      card.classList.toggle('advantage-card--open');
      this.setAttribute('aria-expanded', card.classList.contains('advantage-card--open'));
    });
    head.addEventListener('keydown', function (e) {
      if (e.key === 'Enter' || e.key === ' ') {
        e.preventDefault();
        this.click();
      }
    });
  });

  var callbackBtn = document.querySelector('.btn--callback');
  if (callbackBtn) {
    callbackBtn.addEventListener('click', function () {
      var contacts = document.getElementById('contacts');
      if (contacts) contacts.scrollIntoView({ behavior: 'smooth' });
    });
  }
})();

(function() {
  // Ищем секцию с формой заказа
  const orderSection = document.getElementById('order');
  if (!orderSection) return;

  // Проверяем наличие ошибок валидации внутри секции
  const hasErrors = orderSection.querySelectorAll('.error-text, .input--error, .form-error-summary').length > 0;

  if (hasErrors) {
    // Плавная прокрутка к форме
    orderSection.scrollIntoView({ behavior: 'smooth', block: 'start' });

    // Дополнительно: фокусируем первое поле с ошибкой
    const firstErrorField = orderSection.querySelector('.input--error');
    if (firstErrorField) {
      firstErrorField.focus();
    }
  }
})();