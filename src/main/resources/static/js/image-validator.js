const fileInput = document.getElementById('file-upload');
const preview = document.getElementById('image-preview');
const fileNameSpan = document.querySelector('.file-name');

// Создаём блок для вывода ошибки, если его нет
let errorBlock = document.querySelector('.file-error');
if (!errorBlock) {
    errorBlock = document.createElement('div');
    errorBlock.className = 'error-text file-error';
    errorBlock.style.marginTop = '6px';
    errorBlock.style.display = 'none';
    const wrapper = document.querySelector('.file-upload-wrapper');
    wrapper.parentNode.insertBefore(errorBlock, wrapper.nextSibling);
}

// Функция проверки файла
function validateFile(file) {
    if (!file) return { valid: false, message: '' };

    // 1. Проверка размера (5 МБ)
    const maxSize = 5 * 1024 * 1024;
    if (file.size > maxSize) {
        return {
            valid: false,
            message: 'Размер файла превышает 5 МБ. Пожалуйста, выберите файл меньшего размера.'
        };
    }

    // 2. Проверка типа (JPEG, PNG, WEBP)
    const allowedTypes = ['image/jpeg', 'image/png', 'image/webp'];
    if (!allowedTypes.includes(file.type)) {
        return {
            valid: false,
            message: 'Неподдерживаемый формат файла. Допустимые форматы: JPEG, PNG, WEBP.'
        };
    }

    return { valid: true, message: '' };
}

// Обработчик выбора файла
fileInput.addEventListener('change', function(event) {
    const file = this.files[0];
    const wrapper = document.querySelector('.file-upload-wrapper');

    if (!file) {
        fileNameSpan.textContent = 'Файл не выбран';
        preview.style.backgroundImage = '';
        errorBlock.textContent = '';
        errorBlock.style.display = 'none';
        wrapper.classList.remove('upload-wrapper--error');
        return;
    }

    // Валидация файла
    const validation = validateFile(file);
    if (!validation.valid) {
        fileNameSpan.textContent = file.name;
        errorBlock.textContent = validation.message;
        errorBlock.style.display = 'flex';
        preview.style.backgroundImage = '';
        wrapper.classList.add('upload-wrapper--error');
        return;
    }

    // Валидация пройдена – показываем превью
    fileNameSpan.textContent = file.name;
    errorBlock.textContent = '';
    errorBlock.style.display = 'none';
    wrapper.classList.remove('upload-wrapper--error');

    // Предпросмотр изображения
    const reader = new FileReader();
    reader.onload = function(e) {
        preview.style.backgroundImage = `url(${e.target.result})`;
        preview.style.backgroundSize = 'cover';
        preview.style.backgroundPosition = 'center';
    };
    reader.readAsDataURL(file);


});

const form = document.querySelector('form[action*="/admin/categories/create"]');
if (form) {
    form.addEventListener('submit', function(event) {
        const file = fileInput.files[0];
        if (file) {
            const validation = validateFile(file);
            if (!validation.valid) {
                event.preventDefault();
                errorBlock.textContent = validation.message;
                errorBlock.style.display = 'flex';
                const wrapper = document.querySelector('.file-upload-wrapper');
                wrapper.classList.add('upload-wrapper--error');
                errorBlock.scrollIntoView({ behavior: 'smooth', block: 'center' });
            }
        }
    });
}